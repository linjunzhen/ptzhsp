    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "${JSPNAME}")
    public ModelAndView ${JSPNAME}(HttpServletRequest request) {
        return new ModelAndView("${packageName}/${className?lower_case}/${JSPNAME}");
    }