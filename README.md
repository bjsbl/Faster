Faster
======


1.Faster的缘由<br/>
  不稳定的框架，郁闷的版本冲突，你懂的……<br/>

2.Faster目标<br/>
  2.1 从代码中解脱出，更加专注业务研发<br/>
  2.2 从此再也不用担心版本冲突啦<br/>
  
3.使用方法
   将fast.jar导入到libs；然后修改web.xml,如
	<p>\<filter></p>
		<p>  \<filter-name>FastFilter\</filter-name></p>
		<p>  \<filter-class>com.fast.core.FastFilter\</filter-class></p>
		<p>  \<init-param></p>
			<p>    \<param-name>componentScan\</param-name></p>
			<p>    \<param-value>org.app\</param-value></p>
		<p>  \</init-param></p>
		<p>  \<init-param></p>
			<p>    \<param-name>devMode\</param-name></p>
			<p>    \<param-value>true\</param-value></p>
		<p>  \</init-param></p>
	<p>\</filter></p>
	<p>\<filter-mapping></p>
		<p>  \<filter-name>FastFilter\</filter-name></p>
		<p>  \<url-pattern>/*\</url-pattern></p>
	<p>\</filter-mapping></p>
	
	componentScan是项目java的路径，devMode会输出更多调试信息。

4.遗憾的事
 很少更新github


