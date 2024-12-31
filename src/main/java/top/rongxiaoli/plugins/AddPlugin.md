# 如何添加一个插件并且将其注册至插件命令
由于本人目前还没有找到一个比较好的一次性加载所有命令的方法，所以目前暂时就这么办了。
## Step 1
在`plugins`下新建一个文件夹（软件包），新建一个类，
继承`net.mamoe.mirai.console.plugin.Plugin`类。
我已经提供了三个类：在`top.rongxiaoli.backend.Commands`中，分别是：
- `top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand`
- `top.rongxiaoli.backend.Commands.ArisuBotAbstractRawCommand`
- `top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand`

分别对应mirai框架提供的`JCompositeCommand`，`JRawCommand`和`JSimpleCommand`。
## Step 2
在`top.rongxiaoli.backend.PluginLoader.PluginLoader.addPlugins`中添加上两行：
```java
PluginList.add([你的命令的类].INSTANCE);
INSTANCE.registerCommand([你的命令的类].INSTANCE, false);
```
## Step 3
编写类主要业务代码
