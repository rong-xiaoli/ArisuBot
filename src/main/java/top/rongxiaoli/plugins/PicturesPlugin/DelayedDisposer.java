package top.rongxiaoli.plugins.PicturesPlugin;

import cn.hutool.core.thread.ThreadUtil;
import net.mamoe.mirai.contact.User;
import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.log.ElysiaLogger;

import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedDisposer {
    public DelayedDisposer() {
        this.coolingQueue = new DelayQueue<>();
        this.userHashSet = new LinkedHashSet<>();
        this.LOGGER = new ElysiaLogger();
        this.consumeThread = new Thread(consumer);
    }
    DelayConsumer consumer = new DelayConsumer(this);
    private boolean isLocked = false;
    private final ElysiaLogger LOGGER;
    private final Thread consumeThread;
    private final DelayQueue<CoolingUser> coolingQueue;
    private final LinkedHashSet<Long> userHashSet;
    /**
     * Add user into cooling list using default cooling timer.
     * @param user User object.
     */
    public void AddUser(User user) throws ElementAlreadyExistsException {
        if (userHashSet.contains(user.getId())) {
            throw new ElementAlreadyExistsException(user);
        }
        userHashSet.add(user.getId());
        coolingQueue.add(new CoolingUser(user.getId()));
        LOGGER.verbose("New user added to cooling queue: " + user.getId());
    }

    /**
     * Add user into cooling list.
     * @param user User object.
     * @param intervalSecond Time user to be cooled. Time unit is second.
     */
    public void AddUser(User user, int intervalSecond) throws ElementAlreadyExistsException {
        if (userHashSet.contains(user.getId())) {
            throw new ElementAlreadyExistsException(user);
        }
        userHashSet.add(user.getId());
        coolingQueue.add(new CoolingUser(user.getId(), ((long) intervalSecond) * 1000000));
        LOGGER.verbose("New user added to cooling queue: " + user.getId());
    }

    /**
     * Query the cooling time the user have.
     * @param user Target user.
     * @return Integer indicating how many seconds left to move user out of the queue.
     */
    public long QueryCoolingTime(User user) throws NoSuchElementException {
        if (!userHashSet.contains(user.getId())) {
            return 0;
        }
        for (CoolingUser singleUser :
                coolingQueue) {
            if (singleUser.user == user.getId()) {
                return singleUser.getDelay(TimeUnit.SECONDS);
            }
        }
        throw new NoSuchElementException("There's no such user: " + user.getId());
    }
    public boolean IsLocked() {
        return this.isLocked;
    }

    public void startTiming() {
        consumer = new DelayConsumer(this);
        consumeThread.start();
    }
    public void Shutdown() {
        this.coolingQueue.clear();
        this.userHashSet.clear();
        this.consumer.Shutdown();
        ThreadUtil.safeSleep(1000);
    }
    public static class CoolingUser implements Delayed {
        private final long user;
        private final long availableTime;
        public CoolingUser(long user, long delayedTime) {
            this.user = user;
            this.availableTime = delayedTime + System.currentTimeMillis();
        }
        public CoolingUser(long user) {
            this.user = user;
            this.availableTime = System.currentTimeMillis() + 60000L;
        }
        public long getAvailableTime() {return availableTime;}

        @Override
        public long getDelay(@NotNull TimeUnit unit) {
            long delta = availableTime - System.currentTimeMillis();
            return unit.convert(delta, TimeUnit.MILLISECONDS);
        }
        @Override
        public int compareTo(@NotNull Delayed o) {
            return (int) (this.availableTime - ((CoolingUser)o).getAvailableTime());
        }
    }
    public static class ElementAlreadyExistsException extends Exception {
        private final Object obj;
        public ElementAlreadyExistsException(Object obj) {
            this.obj = obj;
        }
        public ElementAlreadyExistsException(Object obj, String message) {
            super(message);
            this.obj = obj;
        }
        public Object getObj() {return obj;}
    }
    private static class DelayConsumer implements Runnable{
        private boolean isShuttingDown = false;

        private DelayedDisposer disposer;
        private boolean isConsuming = false;
        private final ElysiaLogger LOGGER = new ElysiaLogger();
        public DelayConsumer(DelayedDisposer disposer) {
            this.disposer = disposer;
        }
        public void Shutdown() {
            this.isShuttingDown = true;
        }
        public boolean isConsuming() {return isConsuming;}
        @Override
        public void run() {
            do {
                isConsuming = true;
                if (!ThreadUtil.safeSleep(500)) {
                    LOGGER.warn("DelayConsumer", "Sleep is interrupted. ");
                    isConsuming = false;
                    return;
                }
                CoolingUser u;
                while ((u = disposer.coolingQueue.poll()) != null) {
                    LOGGER.verbose("DelayQueue", "Ejecting delayed element: " + u.user);
                    disposer.userHashSet.remove(u.user);
                }
            } while (!isShuttingDown);
        }
    }
}
