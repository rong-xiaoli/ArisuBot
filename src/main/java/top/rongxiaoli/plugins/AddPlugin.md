# 如何添加一个插件并且将其注册至插件命令
~~由于本人目前还没有找到一个比较好的一次性加载所有命令的方法，所以目前暂时就这么办了。~~

尝试了好久之后，通过使用注解，达成了本项目内的自动类加载。
如果包不在`top.rongxiaoli.ArisuBot`下，则需要在文件
`src/main/java/top/rongxiaoli/backend/PluginLoader/PluginLoader.java`
底下新增一个`checklist.add(YourPlugin.class.getPackage())`。
并不需要每个插件都新增一个`checklist.add()`，不同包才需要。
## Step 1
新建一个类，继承`net.mamoe.mirai.console.plugin.Plugin`类。
我已经提供了三个类：在`top.rongxiaoli.backend.Commands`中，分别是：
- `top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand`
- `top.rongxiaoli.backend.Commands.ArisuBotAbstractRawCommand`
- `top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand`

分别对应mirai框架提供的`JCompositeCommand`，`JRawCommand`和`JSimpleCommand`。
## Step 2
在你的类上添加一个注解：
```java
@top.rongxiaoli.backend.interfaces.Plugin(name = "[YourPluginName]")
```
## Step 3
编写类主要业务代码
