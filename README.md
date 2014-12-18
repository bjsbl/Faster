Faster
======


1.Faster的缘由<br/>
  因为彼人也是从事一项苦逼的工作：码农，人生的一大理想就是做自己想做的事，可是往往事与愿违，每天改来改去的需求，不稳定的框架，郁闷的版本冲突，你懂的……<br/>

如果有同感的可以一起吐槽下，如果有疑问可以邮件给我：bj_sbl@163.com，邮件标题请标明"Faster_<主题>";
这是我第一个GitHub,所以来说有很多不是特别标准的地方，希望大家多多指教；

2.Faster目标<br/>
  2.1 从代码中解脱出来，反正时间都是自己的，能挤多少算多少<br/>
  2.2 依赖最低，不用导入N个JAR，还担心版本的冲突<br/>
  2.3 （待续）<br/>
  
3.使用方法
   将fast.jar导入到libs；然后修改web.xml,如
\<div>	
	<p>\<filter></p>
		<p>\<filter-name>FastFilter\</filter-name></p>
		<p>\<filter-class>com.fast.core.FastFilter\</filter-class></p>
		<p>\<init-param></p>
			<p>\<param-name>componentScan\</param-name></p>
			<p>\<param-value>org.app\</param-value></p>
		<p>\</init-param></p>
		<p>\<init-param></p>
			<p>\<param-name>devMode\</param-name></p>
			<p>\<param-value>true\</param-value></p>
		<p>\</init-param></p>
	<p>\</filter></p>
	<p>\<filter-mapping></p>
		<p>\<filter-name>FastFilter\</filter-name></p>
		<p>\<url-pattern>/*\</url-pattern></p>
	<p>\</filter-mapping></p>
\</div>	
	
	componentScan是项目java的路径，devMode会输出更多调试信息。




