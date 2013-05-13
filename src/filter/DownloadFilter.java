package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;

/**
 * Servlet Filter implementation class DownloadFilter
 */
@WebFilter("/*")
public class DownloadFilter implements Filter {

    /**
     * Default constructor. 
     */
    public DownloadFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		String uri = ((HttpServletRequest) request).getRequestURI();
		String ctx = ((HttpServletRequest) request).getContextPath();
		
		File f = new File(((HttpServletRequest) request).getRealPath(uri.substring(uri.indexOf(ctx) + ctx.length())));
		
		TikaConfig config = TikaConfig.getDefaultConfig();
		MediaType mediaType = config.getMimeRepository().detect(TikaInputStream.get(f), new Metadata());
		MimeType mimeType;
		try {
			
			mimeType = config.getMimeRepository().forName(mediaType.toString());
			String extension = mimeType.getExtension();
			
			response.setContentType(mediaType.toString());
			((HttpServletResponse) response).setHeader("Content-Disposition", "attachment; filename=download" + extension);
			
		} catch (MimeTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
