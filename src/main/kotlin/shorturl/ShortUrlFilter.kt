package win.rushmi0.extension.shorturl

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class ShortUrlFilter : Filter {
    private val service = ShortUrlService()


    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req  = request  as HttpServletRequest
        val res  = response as HttpServletResponse

        req.servletContext.log("[ShortUrl] doFilter called: uri=${req.requestURI} ctx=${req.contextPath}")

        val path = req.requestURI.removePrefix(req.contextPath)
        req.servletContext.log("[ShortUrl] path=$path")

        if (path.startsWith("/short/")) {
            val code   = path.removePrefix("/short/")
            req.servletContext.log("[ShortUrl] looking up code=[$code]")

            val target = service.resolve(code)
            req.servletContext.log("[ShortUrl] resolved=[$target]")

            if (target != null) {
                req.servletContext.log("[ShortUrl] redirecting to $target")
                res.sendRedirect(target)
                return
            } else {
                req.servletContext.log("[ShortUrl] code not found or ACTIVE != '1'")
            }
        }

        chain.doFilter(request, response)
    }
}