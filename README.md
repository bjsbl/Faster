1.Faster的缘由
不稳定的框架，郁闷的版本冲突，你懂的……

2.使用方法 将fast.jar导入到libs；然后修改web.xml,如

    <filter>
        <filter-name>FastFilter</filter-name>
            <filter-class>com.fast.core.FastFilter</filter-class>
          <init-param>
                <param-name>componentScan</param-name>
                <param-value>org.app</param-value>
          </init-param>
          <init-param>
                <param-name>devMode</param-name>
                <param-value>true</param-value>
          </init-param>
    </filter>
    <filter-mapping>
          <filter-name>FastFilter</filter-name>
          <url-pattern>/*</url-pattern>
    </filter-mapping>

  
componentScan是项目java的路径，devMode会输出更多调试信息。


