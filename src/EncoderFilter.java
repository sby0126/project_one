

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * �����̳� JSP���� �������� ó���ؾ� �� �۾��� ���Ϳ��� ������ �� �ֽ��ϴ�.
 * �� Ŭ������ ��� request ����� ���� ���ڵ��� UTF-8�� �����ϴ� ������ �մϴ�.
 */
@WebFilter("/*")
public class EncoderFilter implements Filter {
	
	private ServletContext mContext;

    public EncoderFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");

		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		mContext = fConfig.getServletContext();
	}

}
