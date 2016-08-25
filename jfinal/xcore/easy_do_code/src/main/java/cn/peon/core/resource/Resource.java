
package cn.peon.core.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 代表一个资源。
 * <p>
 * 一个资源可以被表示成<code>URL</code>、<code>File</code>或是<code>InputStream</code>。
 * 需要注意的是，不是所有类型的资源都可以同时取得上述三种形式。
 * </p>
 *
 * @author Michael Zhou
 */
public interface Resource {
    /** 取得资源的<code>URL</code>。如果资源不能被表示成<code>URL</code>，则返回<code>null</code>。 */
    URL getURL();

    /** 取得资源的<code>File</code>。如果资源不能被表示成<code>File</code>，则返回<code>null</code>。 */
    File getFile();

    /**
     * 取得资源的<code>InputStream</code>。如果资源不能被表示成<code>InputStream</code>，则返回
     * <code>null</code>。
     */
    InputStream getInputStream() throws IOException;

    /**
     * 判断资源是否存在。
     *
     * @return 如果存在，则返回<code>true</code>
     */
    boolean exists();

    /** 取得资源最近修改时间（ms）。如果不支持，则返回<code>0</code>。 */
    long lastModified();

    /** 资源应该实现该方法。 */
    int hashCode();

    /** 资源应该实现该方法。 */
    boolean equals(Object other);
}
