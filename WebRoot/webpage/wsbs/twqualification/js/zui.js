/*!
 * ZUI: Standard edition - v1.8.1 - 2018-01-18
 * http://zui.sexy
 * GitHub: https://github.com/easysoft/zui.git 
 * Copyright (c) 2018 cnezsoft.com; Licensed MIT
 */
/*! Some code copy from Bootstrap v3.0.0 by @fat and @mdo. (Copyright 2013 Twitter, Inc. Licensed under http://www.apache.org/licenses/)*/
!function (t, e, i) {
    "use strict";
    if ("undefined" == typeof t)throw new Error("ZUI requires jQuery");
    t.zui || (t.zui = function (e) {
        t.isPlainObject(e) && t.extend(t.zui, e)
    });
    var n = {all: -1, left: 0, middle: 1, right: 2}, o = 0;
    t.zui({
        uuid: function () {
            return 1e3 * (new Date).getTime() + o++ % 1e3
        }, callEvent: function (e, n, o) {
            if (t.isFunction(e)) {
                o !== i && (e = t.proxy(e, o));
                var a = e(n);
                return n && (n.result = a), !(a !== i && !a)
            }
            return 1
        }, clientLang: function () {
            var i, n = e.config;
            if ("undefined" != typeof n && n.clientLang && (i = n.clientLang), !i) {
                var o = t("html").attr("lang");
                i = o ? o : navigator.userLanguage || navigator.userLanguage || "zh_cn"
            }
            return i.replace("-", "_").toLowerCase()
        }, strCode: function (t) {
            var e = 0;
            if (t && t.length)for (var i = 0; i < t.length; ++i)e += i * t.charCodeAt(i);
            return e
        }, getMouseButtonCode: function (t) {
            return "number" != typeof t && (t = n[t]), t !== i && null !== t || (t = -1), t
        }
    }), t.fn.callEvent = function (e, n, o) {
        var a = t(this), s = e.indexOf(".zui."), r = s < 0 ? e : e.substring(0, s), l = t.Event(r, n);
        if (o === i && s > 0 && (o = a.data(e.substring(s + 1))), o && o.options) {
            var c = o.options[r];
            t.isFunction(c) && (l.result = t.zui.callEvent(c, l, o))
        }
        return a.trigger(l), l
    }, t.fn.callComEvent = function (e, n, o) {
        o === i || t.isArray(o) || (o = [o]);
        var a = this, s = a.triggerHandler(n, o), r = e.options[n];
        return r && (s = r.apply(e, o)), s
    }
}(jQuery, window, void 0), function (t) {
    "use strict";
    t.fn.fixOlPd = function (e) {
        return e = e || 10, this.each(function () {
            var i = t(this);
            i.css("paddingLeft", Math.ceil(Math.log10(i.children().length)) * e + 10)
        })
    }, t(function () {
        t(".ol-pd-fix,.article ol").fixOlPd()
    })
}(jQuery), +function (t) {
    "use strict";
    var e = function (i, n) {
        this.$element = t(i), this.options = t.extend({}, e.DEFAULTS, n), this.isLoading = !1
    };
    e.DEFAULTS = {loadingText: "loading..."}, e.prototype.setState = function (e) {
        var i = "disabled", n = this.$element, o = n.is("input") ? "val" : "html", a = n.data();
        e += "Text", a.resetText || n.data("resetText", n[o]()), n[o](a[e] || this.options[e]), setTimeout(t.proxy(function () {
            "loadingText" == e ? (this.isLoading = !0, n.addClass(i).attr(i, i)) : this.isLoading && (this.isLoading = !1, n.removeClass(i).removeAttr(i))
        }, this), 0)
    }, e.prototype.toggle = function () {
        var t = !0, e = this.$element.closest('[data-toggle="buttons"]');
        if (e.length) {
            var i = this.$element.find("input");
            "radio" == i.prop("type") && (i.prop("checked") && this.$element.hasClass("active") ? t = !1 : e.find(".active").removeClass("active")), t && i.prop("checked", !this.$element.hasClass("active")).trigger("change")
        }
        t && this.$element.toggleClass("active")
    };
    var i = t.fn.button;
    t.fn.button = function (i) {
        return this.each(function () {
            var n = t(this), o = n.data("zui.button"), a = "object" == typeof i && i;
            o || n.data("zui.button", o = new e(this, a)), "toggle" == i ? o.toggle() : i && o.setState(i)
        })
    }, t.fn.button.Constructor = e, t.fn.button.noConflict = function () {
        return t.fn.button = i, this
    }, t(document).on("click.zui.button.data-api", "[data-toggle^=button]", function (e) {
        var i = t(e.target);
        i.hasClass("btn") || (i = i.closest(".btn")), i.button("toggle"), e.preventDefault()
    })
}(jQuery), +function (t) {
    "use strict";
    var e = '[data-dismiss="alert"]', i = "zui.alert", n = function (i) {
        t(i).on("click", e, this.close)
    };
    n.prototype.close = function (e) {
        function n() {
            s.trigger("closed." + i).remove()
        }

        var o = t(this), a = o.attr("data-target");
        a || (a = o.attr("href"), a = a && a.replace(/.*(?=#[^\s]*$)/, ""));
        var s = t(a);
        e && e.preventDefault(), s.length || (s = o.hasClass("alert") ? o : o.parent()), s.trigger(e = t.Event("close." + i)), e.isDefaultPrevented() || (s.removeClass("in"), t.support.transition && s.hasClass("fade") ? s.one(t.support.transition.end, n).emulateTransitionEnd(150) : n())
    };
    var o = t.fn.alert;
    t.fn.alert = function (e) {
        return this.each(function () {
            var o = t(this), a = o.data(i);
            a || o.data(i, a = new n(this)), "string" == typeof e && a[e].call(o)
        })
    }, t.fn.alert.Constructor = n, t.fn.alert.noConflict = function () {
        return t.fn.alert = o, this
    }, t(document).on("click." + i + ".data-api", e, n.prototype.close)
}(window.jQuery), function (t) {
    "use strict";
    var e = "zui.pager", i = {page: 0, recTotal: 0, recPerPage: 10}, n = {
        zh_cn: {
            prev: "上一页",
            next: "下一页",
            first: "第一页",
            last: "最后一页",
            "goto": "跳转",
            pageOf: "第 <strong>{page}</strong> 页",
            totalPage: "共 <strong>{totalPage}</strong> 页",
            totalCount: "共 <strong>{recTotal}</strong> 项",
            pageSize: "每页 <strong>{recPerPage}</strong> 项",
            itemsRange: "第 <strong>{start}</strong> ~ <strong>{end}</strong> 项",
            pageOfTotal: "第 <strong>{page}</strong>/<strong>{totalPage}</strong> 页"
        },
        zh_tw: {
            prev: "上一頁",
            next: "下一頁",
            first: "第一頁",
            last: "最後一頁",
            "goto": "跳轉",
            pageOf: "第 <strong>{page}</strong> 頁",
            totalPage: "共 <strong>{totalPage}</strong> 頁",
            totalCount: "共 <strong>{recTotal}</strong> 項",
            pageSize: "每頁 <strong>{recPerPage}</strong> 項",
            itemsRange: "第 <strong>{start}</strong> ~ <strong>{end}</strong> 項",
            pageOfTotal: "第 <strong>{page}</strong>/<strong>{totalPage}</strong> 頁"
        },
        en: {
            prev: "Prev",
            next: "Next",
            first: "First",
            last: "Last",
            "goto": "Goto",
            pageOf: "Page <strong>{page}</strong>",
            totalPage: "<strong>{totalPage}</strong> pages",
            totalCount: "<strong>{recTotal}</strong> items",
            pageSize: "<strong>{recPerPage}</strong> items per page",
            itemsRange: "From <strong>{start}</strong> to <strong>{end}</strong>",
            pageOfTotal: "Page <strong>{page}</strong> of <strong>{totalPage}</strong>"
        }
    }, o = function (i, a) {
        var s = this;
        s.name = e, s.$ = t(i), a = s.options = t.extend({}, o.DEFAULTS, this.$.data(), a);
        var r = a.lang || "zh_cn";
        s.lang = t.isPlainObject(r) ? t.extend(!0, {}, n[r.lang || t.zui.clientLang()], r) : n[r], s.state = {}, s.set(a.page, a.recTotal, a.recPerPage), s.$.on("click", ".pager-goto-btn", function () {
            var e = t(this).closest(".pager-goto"), i = parseInt(e.find(".pager-goto-input").val());
            NaN !== i && s.set(i)
        }).on("click", ".pager-item", function () {
            var e = t(this).data("page");
            "number" == typeof e && e > 0 && s.set(e)
        }).on("click", ".pager-size-menu [data-size]", function () {
            var e = t(this).data("size");
            "number" == typeof e && e > 0 && s.set(-1, -1, e)
        })
    };
    o.prototype.set = function (e, n, o) {
        var a = this;
        "object" == typeof e && null !== e && (o = e.recPerPage, n = e.recTotal, e = e.page);
        var s = a.state;
        s || (s = t.extend({}, i));
        var r = t.extend({}, s);
        return "number" == typeof o && o > 0 && (s.recPerPage = o), "number" == typeof n && n >= 0 && (s.recTotal = n), "number" == typeof e && e >= 0 && (s.page = e), s.totalPage = s.recTotal && s.recPerPage ? Math.ceil(s.recTotal / s.recPerPage) : 1, s.page = Math.max(0, Math.min(s.page, s.totalPage)), s.pageRecCount = s.recTotal, s.page && s.recTotal && (s.page < s.totalPage ? s.pageRecCount = s.recPerPage : s.page > 1 && (s.pageRecCount = s.recTotal - s.recPerPage * (s.page - 1))), s.skip = s.page > 1 ? (s.page - 1) * s.recPerPage : 0, s.start = s.skip + 1, s.end = s.skip + s.pageRecCount, s.prev = s.page > 1 ? s.page - 1 : 0, s.next = s.page < s.totalPage ? s.page + 1 : 0, a.state = s, r.page === s.page && r.recTotal === s.recTotal && r.recPerPage === s.recPerPage || a.$.callComEvent(a, "onPageChange", [s, r]), a.render()
    }, o.prototype.createLinkItem = function (e, i, n) {
        var o = this;
        void 0 === i && (i = e);
        var a = t('<a class="pager-item" data-page="' + e + '"/>').attr("href", e ? o.createLink(e, o.state) : "###").html(i);
        return n || (a = t("<li />").append(a).toggleClass("active", e === o.state.page).toggleClass("disabled", !e)), a
    }, o.prototype.createNavItems = function (t) {
        var e = this, i = e.$, n = e.state, o = n.totalPage, a = n.page, s = function (t, n) {
            if (t === !1)return void i.append(e.createLinkItem(0, n || '<i class="icon icon-ellipsis-h"></i>'));
            void 0 === n && (n = t);
            for (var o = t; o <= n; ++o)i.append(e.createLinkItem(o))
        };
        void 0 === t && (t = e.options.maxNavCount || 10), s(1), o > 1 && (o <= t ? s(2, o) : a < t - 2 ? (s(2, t - 2), s(!1), s(o)) : a > o - t + 2 ? (s(!1), s(o - t + 2, o)) : (s(!1), s(a - Math.ceil((t - 4) / 2), a + Math.floor((t - 4) / 2)), s(!1), s(o)))
    }, o.prototype.createGoto = function () {
        var e = this, i = this.state,
            n = t('<div class="input-group pager-goto" style="width: ' + (35 + 9 * (i.page + "").length + 25 + 12 * e.lang["goto"].length) + 'px"><input value="' + i.page + '" type="number" min="1" max="' + i.totalPage + '" placeholder="' + i.page + '" class="form-control pager-goto-input"><span class="input-group-btn"><button class="btn pager-goto-btn" type="button">' + e.lang["goto"] + "</button></span></div>");
        return n
    }, o.prototype.createSizeMenu = function () {
        var e = this, i = this.state, n = t('<ul class="dropdown-menu"></ul>'), o = e.options.pageSizeOptions;
        "string" == typeof o && (o = o.split(","));
        for (var a = 0; a < o.length; ++a) {
            var s = o[a];
            "string" == typeof s && (s = parseInt(s));
            var r = t('<li><a href="###" data-size="' + s + '">' + s + "</a></li>").toggleClass("active", s === i.recPerPage);
            n.append(r)
        }
        return t('<div class="btn-group pager-size-menu"><button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">' + e.lang.pageSize.format(i) + ' <span class="caret"></span></button></div>').addClass(e.options.menuDirection).append(n)
    }, o.prototype.createElement = function (e, i, n) {
        var o = this, a = t.proxy(o.createLinkItem, o), s = o.lang;
        switch (e) {
            case"prev":
                return a(n.prev, s.prev);
            case"prev_icon":
                return a(n.prev, '<i class="icon ' + o.options.prevIcon + '"></i>');
            case"next":
                return a(n.next, s.next);
            case"next_icon":
                return a(n.next, '<i class="icon ' + o.options.nextIcon + '"></i>');
            case"first":
                return a(1, s.first, !0);
            case"first_icon":
                return a(1, '<i class="icon ' + o.options.firstIcon + '"></i>', !0);
            case"last":
                return a(n.totalPage, s.last, !0);
            case"last_icon":
                return a(n.totalPage, '<i class="icon ' + o.options.lastIcon + '"></i>', !0);
            case"space":
            case"|":
                return t('<li class="space" />');
            case"nav":
            case"pages":
                return void o.createNavItems();
            case"total_text":
                return t(('<div class="pager-label">' + s.totalCount + "</div>").format(n));
            case"page_text":
                return t(('<div class="pager-label">' + s.pageOf + "</div>").format(n));
            case"total_page_text":
                return t(('<div class="pager-label">' + s.totalPage + "</div>").format(n));
            case"page_of_total_text":
                return t(('<div class="pager-label">' + s.pageOfTotal + "</div>").format(n));
            case"page_size_text":
                return t(('<div class="pager-label">' + s.pageSize + "</div>").format(n));
            case"items_range_text":
                return t(('<div class="pager-label">' + s.itemsRange + "</div>").format(n));
            case"goto":
                return o.createGoto();
            case"size_menu":
                return o.createSizeMenu();
            default:
                return t("<li/>").html(e)
        }
    }, o.prototype.createLink = function (e, i) {
        var n = this.options.linkCreator;
        return "string" == typeof n ? n.format(t.extend({}, i, {page: e})) : t.isFunction(n) ? n(e, i) : "#page=" + e
    }, o.prototype.render = function (e) {
        var i = this, n = i.state, o = i.options.elementCreator || i.createElement, a = t.isPlainObject(o);
        e = e || i.elements || i.options.elements, "string" == typeof e && (e = e.split(",")), i.elements = e, i.$.empty();
        for (var s = 0; s < e.length; ++s) {
            var r = t.trim(e[s]), l = a ? o[r] || o : o, c = l.call(i, r, i.$, n);
            c === !1 && (c = i.createElement(r, i.$, n)), c instanceof t && ("LI" !== c[0].tagName && (c = t("<li/>").append(c)), i.$.append(c))
        }
        var d = null;
        return i.$.children("li").each(function () {
            var e = t(this), i = !!e.children(".pager-item").length;
            d ? d.toggleClass("pager-item-right", !i) : i && e.addClass("pager-item-left"), d = i ? e : null
        }), i.$.callComEvent(i, "onRender", [n]), i
    }, o.DEFAULTS = t.extend({
        elements: ["first_icon", "prev_icon", "pages", "next_icon", "last_icon", "page_of_total_text", "items_range_text", "total_text"],
        prevIcon: "icon-double-angle-left",
        nextIcon: "icon-double-angle-right",
        firstIcon: "icon-step-backward",
        lastIcon: "icon-step-forward",
        maxNavCount: 10,
        menuDirection: "dropdown",
        pageSizeOptions: [10, 20, 30, 50, 100]
    }, i), t.fn.pager = function (i) {
        return this.each(function () {
            var n = t(this), a = n.data(e), s = "object" == typeof i && i;
            a || n.data(e, a = new o(this, s)), "string" == typeof i && a[i]()
        })
    }, o.NAME = e, t.fn.pager.Constructor = o, t(function () {
        t('[data-ride="pager"]').pager()
    })
}(jQuery), +function (t) {
    "use strict";
    var e = "zui.tab", i = function (e) {
        this.element = t(e)
    };
    i.prototype.show = function () {
        var i = this.element, n = i.closest("ul:not(.dropdown-menu)"), o = i.attr("data-target") || i.attr("data-tab");
        if (o || (o = i.attr("href"), o = o && o.replace(/.*(?=#[^\s]*$)/, "")), !i.parent("li").hasClass("active")) {
            var a = n.find(".active:last a")[0], s = t.Event("show." + e, {relatedTarget: a});
            if (i.trigger(s), !s.isDefaultPrevented()) {
                var r = t(o);
                this.activate(i.parent("li"), n), this.activate(r, r.parent(), function () {
                    i.trigger({type: "shown." + e, relatedTarget: a})
                })
            }
        }
    }, i.prototype.activate = function (e, i, n) {
        function o() {
            a.removeClass("active").find("> .dropdown-menu > .active").removeClass("active"), e.addClass("active"), s ? (e[0].offsetWidth, e.addClass("in")) : e.removeClass("fade"), e.parent(".dropdown-menu") && e.closest("li.dropdown").addClass("active"), n && n()
        }

        var a = i.find("> .active"), s = n && t.support.transition && a.hasClass("fade");
        s ? a.one(t.support.transition.end, o).emulateTransitionEnd(150) : o(), a.removeClass("in")
    };
    var n = t.fn.tab;
    t.fn.tab = function (n) {
        return this.each(function () {
            var o = t(this), a = o.data(e);
            a || o.data(e, a = new i(this)), "string" == typeof n && a[n]()
        })
    }, t.fn.tab.Constructor = i, t.fn.tab.noConflict = function () {
        return t.fn.tab = n, this
    }, t(document).on("click.zui.tab.data-api", '[data-toggle="tab"], [data-tab]', function (e) {
        e.preventDefault(), t(this).tab("show")
    })
}(window.jQuery), +function (t) {
    "use strict";
    function e() {
        var t = document.createElement("bootstrap"), e = {
            WebkitTransition: "webkitTransitionEnd",
            MozTransition: "transitionend",
            OTransition: "oTransitionEnd otransitionend",
            transition: "transitionend"
        };
        for (var i in e)if (void 0 !== t.style[i])return {end: e[i]};
        return !1
    }

    t.fn.emulateTransitionEnd = function (e) {
        var i = !1, n = this;
        t(this).one("bsTransitionEnd", function () {
            i = !0
        });
        var o = function () {
            i || t(n).trigger(t.support.transition.end)
        };
        return setTimeout(o, e), this
    }, t(function () {
        t.support.transition = e(), t.support.transition && (t.event.special.bsTransitionEnd = {
            bindType: t.support.transition.end,
            delegateType: t.support.transition.end,
            handle: function (e) {
                if (t(e.target).is(this))return e.handleObj.handler.apply(this, arguments)
            }
        })
    })
}(jQuery), +function (t) {
    "use strict";
    var e = "zui.collapse", i = function (e, n) {
        this.$element = t(e), this.options = t.extend({}, i.DEFAULTS, n), this.transitioning = null, this.options.parent && (this.$parent = t(this.options.parent)), this.options.toggle && this.toggle()
    };
    i.DEFAULTS = {toggle: !0}, i.prototype.dimension = function () {
        var t = this.$element.hasClass("width");
        return t ? "width" : "height"
    }, i.prototype.show = function () {
        if (!this.transitioning && !this.$element.hasClass("in")) {
            var i = t.Event("show." + e);
            if (this.$element.trigger(i), !i.isDefaultPrevented()) {
                var n = this.$parent && this.$parent.find(".in");
                if (n && n.length) {
                    var o = n.data(e);
                    if (o && o.transitioning)return;
                    n.collapse("hide"), o || n.data(e, null)
                }
                var a = this.dimension();
                this.$element.removeClass("collapse").addClass("collapsing")[a](0), this.transitioning = 1;
                var s = function () {
                    this.$element.removeClass("collapsing").addClass("in")[a]("auto"), this.transitioning = 0, this.$element.trigger("shown." + e)
                };
                if (!t.support.transition)return s.call(this);
                var r = t.camelCase(["scroll", a].join("-"));
                this.$element.one(t.support.transition.end, t.proxy(s, this)).emulateTransitionEnd(350)[a](this.$element[0][r])
            }
        }
    }, i.prototype.hide = function () {
        if (!this.transitioning && this.$element.hasClass("in")) {
            var i = t.Event("hide." + e);
            if (this.$element.trigger(i), !i.isDefaultPrevented()) {
                var n = this.dimension();
                this.$element[n](this.$element[n]())[0].offsetHeight, this.$element.addClass("collapsing").removeClass("collapse").removeClass("in"), this.transitioning = 1;
                var o = function () {
                    this.transitioning = 0, this.$element.trigger("hidden." + e).removeClass("collapsing").addClass("collapse")
                };
                return t.support.transition ? void this.$element[n](0).one(t.support.transition.end, t.proxy(o, this)).emulateTransitionEnd(350) : o.call(this)
            }
        }
    }, i.prototype.toggle = function () {
        this[this.$element.hasClass("in") ? "hide" : "show"]()
    };
    var n = t.fn.collapse;
    t.fn.collapse = function (n) {
        return this.each(function () {
            var o = t(this), a = o.data(e), s = t.extend({}, i.DEFAULTS, o.data(), "object" == typeof n && n);
            a || o.data(e, a = new i(this, s)), "string" == typeof n && a[n]()
        })
    }, t.fn.collapse.Constructor = i, t.fn.collapse.noConflict = function () {
        return t.fn.collapse = n, this
    }, t(document).on("click." + e + ".data-api", "[data-toggle=collapse]", function (i) {
        var n, o = t(this),
            a = o.attr("data-target") || i.preventDefault() || (n = o.attr("href")) && n.replace(/.*(?=#[^\s]+$)/, ""),
            s = t(a), r = s.data(e), l = r ? "toggle" : o.data(), c = o.attr("data-parent"), d = c && t(c);
        r && r.transitioning || (d && d.find('[data-toggle=collapse][data-parent="' + c + '"]').not(o).addClass("collapsed"), o[s.hasClass("in") ? "addClass" : "removeClass"]("collapsed")), s.collapse(l)
    })
}(window.jQuery), function (t, e) {
    "use strict";
    var i = 1200, n = 992, o = 768, a = e(t), s = function () {
        var t = a.width();
        e("html").toggleClass("screen-desktop", t >= n && t < i).toggleClass("screen-desktop-wide", t >= i).toggleClass("screen-tablet", t >= o && t < n).toggleClass("screen-phone", t < o).toggleClass("device-mobile", t < n).toggleClass("device-desktop", t >= n)
    }, r = "", l = navigator.userAgent;
    l.match(/(iPad|iPhone|iPod)/i) ? r += " os-ios" : l.match(/android/i) ? r += " os-android" : l.match(/Win/i) ? r += " os-windows" : l.match(/Mac/i) ? r += " os-mac" : l.match(/Linux/i) ? r += " os-linux" : l.match(/X11/i) && (r += " os-unix"), "ontouchstart" in document.documentElement && (r += " is-touchable"), e("html").addClass(r), a.resize(s), s()
}(window, jQuery), function (t) {
    "use strict";
    var e = {
        zh_cn: '您的浏览器版本过低，无法体验所有功能，建议升级或者更换浏览器。 <a href="http://browsehappy.com/" target="_blank" class="alert-link">了解更多...</a>',
        zh_tw: '您的瀏覽器版本過低，無法體驗所有功能，建議升級或者更换瀏覽器。<a href="http://browsehappy.com/" target="_blank" class="alert-link">了解更多...</a>',
        en: 'Your browser is too old, it has been unable to experience the colorful internet. We strongly recommend that you upgrade a better one. <a href="http://browsehappy.com/" target="_blank" class="alert-link">Learn more...</a>'
    }, i = function () {
        var t = this.isIE() || this.isIE10() || !1;
        if (t)for (var e = 10; e > 5; e--)if (this.isIE(e)) {
            t = e;
            break
        }
        this.ie = t, this.cssHelper()
    };
    i.prototype.cssHelper = function () {
        var e = this.ie, i = t("html");
        i.toggleClass("ie", e).removeClass("ie-6 ie-7 ie-8 ie-9 ie-10"), e && i.addClass("ie-" + e).toggleClass("gt-ie-7 gte-ie-8 support-ie", e >= 8).toggleClass("lte-ie-7 lt-ie-8 outdated-ie", e < 8).toggleClass("gt-ie-8 gte-ie-9", e >= 9).toggleClass("lte-ie-8 lt-ie-9", e < 9).toggleClass("gt-ie-9 gte-ie-10", e >= 10).toggleClass("lte-ie-9 lt-ie-10", e < 10)
    }, i.prototype.tip = function (i) {
        var n = t("#browseHappyTip");
        n.length || (n = t('<div id="browseHappyTip" class="alert alert-dismissable alert-danger-inverse alert-block" style="position: relative; z-index: 99999"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button><div class="container"><div class="content text-center"></div></div></div>'), n.prependTo("body")), n.find(".content").html(i || this.browseHappyTip || e[t.zui.clientLang() || "zh_cn"])
    }, i.prototype.isIE = function (t) {
        if (10 === t)return this.isIE10();
        var e = document.createElement("b");
        return e.innerHTML = "<!--[if IE " + (t || "") + "]><i></i><![endif]-->", 1 === e.getElementsByTagName("i").length
    }, i.prototype.isIE10 = function () {
        return !1
    }, t.zui({browser: new i}), t(function () {
        t("body").hasClass("disabled-browser-tip") || t.zui.browser.ie && t.zui.browser.ie < 8 && t.zui.browser.tip()
    })
}(jQuery), function () {
    "use strict";
    Date.ONEDAY_TICKS = 864e5, Date.prototype.format || (Date.prototype.format = function (t) {
        var e = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S+": this.getMilliseconds()
        };
        /(y+)/i.test(t) && (t = t.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length)));
        for (var i in e)new RegExp("(" + i + ")").test(t) && (t = t.replace(RegExp.$1, 1 == RegExp.$1.length ? e[i] : ("00" + e[i]).substr(("" + e[i]).length)));
        return t
    }), Date.prototype.addMilliseconds || (Date.prototype.addMilliseconds = function (t) {
        return this.setTime(this.getTime() + t), this
    }), Date.prototype.addDays || (Date.prototype.addDays = function (t) {
        return this.addMilliseconds(t * Date.ONEDAY_TICKS), this
    }), Date.prototype.clone || (Date.prototype.clone = function () {
        var t = new Date;
        return t.setTime(this.getTime()), t
    }), Date.isLeapYear || (Date.isLeapYear = function (t) {
        return t % 4 === 0 && t % 100 !== 0 || t % 400 === 0
    }), Date.getDaysInMonth || (Date.getDaysInMonth = function (t, e) {
        return [31, Date.isLeapYear(t) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][e]
    }), Date.prototype.isLeapYear || (Date.prototype.isLeapYear = function () {
        return Date.isLeapYear(this.getFullYear())
    }), Date.prototype.clearTime || (Date.prototype.clearTime = function () {
        return this.setHours(0), this.setMinutes(0), this.setSeconds(0), this.setMilliseconds(0), this
    }), Date.prototype.getDaysInMonth || (Date.prototype.getDaysInMonth = function () {
        return Date.getDaysInMonth(this.getFullYear(), this.getMonth())
    }), Date.prototype.addMonths || (Date.prototype.addMonths = function (t) {
        var e = this.getDate();
        return this.setDate(1), this.setMonth(this.getMonth() + t), this.setDate(Math.min(e, this.getDaysInMonth())), this
    }), Date.prototype.getLastWeekday || (Date.prototype.getLastWeekday = function (t) {
        t = t || 1;
        for (var e = this.clone(); e.getDay() != t;)e.addDays(-1);
        return e.clearTime(), e
    }), Date.prototype.isSameDay || (Date.prototype.isSameDay = function (t) {
        return t.toDateString() === this.toDateString()
    }), Date.prototype.isSameWeek || (Date.prototype.isSameWeek = function (t) {
        var e = this.getLastWeekday(), i = e.clone().addDays(7);
        return t >= e && t < i
    }), Date.prototype.isSameYear || (Date.prototype.isSameYear = function (t) {
        return this.getFullYear() === t.getFullYear()
    }), Date.create || (Date.create = function (t) {
        return t instanceof Date || ("number" == typeof t && t < 1e10 && (t *= 1e3), t = new Date(t)), t
    }), Date.timestamp || (Date.timestamp = function (t) {
        return "number" == typeof t ? t < 1e10 && (t *= 1e3) : t = Date.create(t).getTime(), t
    })
}(), function () {
    "use strict";
    String.prototype.format || (String.prototype.format = function (t) {
        var e = this;
        if (arguments.length > 0) {
            var i;
            if (arguments.length <= 2 && "object" == typeof t)for (var n in t)void 0 !== t[n] && (i = new RegExp("(" + (arguments[1] ? arguments[1].replace("0", n) : "{" + n + "}") + ")", "g"), e = e.replace(i, t[n])); else for (var o = 0; o < arguments.length; o++)void 0 !== arguments[o] && (i = new RegExp("({[" + o + "]})", "g"), e = e.replace(i, arguments[o]))
        }
        return e
    }), String.prototype.isNum || (String.prototype.isNum = function (t) {
        if (null !== t) {
            var e, i;
            return i = /\d*/i, e = t.match(i), e == t
        }
        return !1
    }), String.prototype.endsWith || (String.prototype.endsWith = function (t, e) {
        var i = this.toString();
        (void 0 === e || e > i.length) && (e = i.length), e -= t.length;
        var n = i.indexOf(t, e);
        return n !== -1 && n === e
    }), String.prototype.startsWith || (String.prototype.startsWith = function (t, e) {
        return e = e || 0, this.lastIndexOf(t, e) === e
    }), String.prototype.includes || (String.prototype.includes = function () {
        return String.prototype.indexOf.apply(this, arguments) !== -1
    })
}(), /*!
 * jQuery resize event - v1.1
 * http://benalman.com/projects/jquery-resize-plugin/
 * Copyright (c) 2010 "Cowboy" Ben Alman
 * MIT & GPL http://benalman.com/about/license/
 */
    function (t, e, i) {
        "$:nomunge";
        function n() {
            o = e[r](function () {
                a.each(function () {
                    var e = t(this), i = e.width(), n = e.height(), o = t.data(this, c);
                    i === o.w && n === o.h || e.trigger(l, [o.w = i, o.h = n])
                }), n()
            }, s[d])
        }

        var o, a = t([]), s = t.resize = t.extend(t.resize, {}), r = "setTimeout", l = "resize",
            c = l + "-special-event", d = "delay", p = "throttleWindow";
        s[d] = 250, s[p] = !0, t.event.special[l] = {
            setup: function () {
                if (!s[p] && this[r])return !1;
                var e = t(this);
                a = a.add(e), t.data(this, c, {w: e.width(), h: e.height()}), 1 === a.length && n()
            }, teardown: function () {
                if (!s[p] && this[r])return !1;
                var e = t(this);
                a = a.not(e), e.removeData(c), a.length || clearTimeout(o)
            }, add: function (e) {
                function n(e, n, a) {
                    var s = t(this), r = t.data(this, c) || {};
                    r.w = n !== i ? n : s.width(), r.h = a !== i ? a : s.height(), o.apply(this, arguments)
                }

                if (!s[p] && this[r])return !1;
                var o;
                return t.isFunction(e) ? (o = e, n) : (o = e.handler, void(e.handler = n))
            }
        }
    }(jQuery, this), +function (t) {
    "use strict";
    function e(n, o) {
        var a, s = t.proxy(this.process, this);
        this.$element = t(t(n).is("body") ? window : n), this.$body = t("body"), this.$scrollElement = this.$element.on("scroll." + i + ".data-api", s), this.options = t.extend({}, e.DEFAULTS, o), this.selector || (this.selector = (this.options.target || (a = t(n).attr("href")) && a.replace(/.*(?=#[^\s]+$)/, "") || "") + " .nav li > a"), this.offsets = t([]), this.targets = t([]), this.activeTarget = null, this.refresh(), this.process()
    }

    var i = "zui.scrollspy";
    e.DEFAULTS = {offset: 10}, e.prototype.refresh = function () {
        var e = this.$element[0] == window ? "offset" : "position";
        this.offsets = t([]), this.targets = t([]);
        var i = this;
        this.$body.find(this.selector).map(function () {
            var n = t(this), o = n.data("target") || n.attr("href"), a = /^#./.test(o) && t(o);
            return a && a.length && a.is(":visible") && [[a[e]().top + (!t.isWindow(i.$scrollElement.get(0)) && i.$scrollElement.scrollTop()), o]] || null
        }).sort(function (t, e) {
            return t[0] - e[0]
        }).each(function () {
            i.offsets.push(this[0]), i.targets.push(this[1])
        })
    }, e.prototype.process = function () {
        var t, e = this.$scrollElement.scrollTop() + this.options.offset,
            i = this.$scrollElement[0].scrollHeight || this.$body[0].scrollHeight, n = i - this.$scrollElement.height(),
            o = this.offsets, a = this.targets, s = this.activeTarget;
        if (e >= n)return s != (t = a.last()[0]) && this.activate(t);
        if (s && e <= o[0])return s != (t = a[0]) && this.activate(t);
        for (t = o.length; t--;)s != a[t] && e >= o[t] && (!o[t + 1] || e <= o[t + 1]) && this.activate(a[t])
    }, e.prototype.activate = function (e) {
        this.activeTarget = e, t(this.selector).parentsUntil(this.options.target, ".active").removeClass("active");
        var n = this.selector + '[data-target="' + e + '"],' + this.selector + '[href="' + e + '"]',
            o = t(n).parents("li").addClass("active");
        o.parent(".dropdown-menu").length && (o = o.closest("li.dropdown").addClass("active")), o.trigger("activate." + i)
    };
    var n = t.fn.scrollspy;
    t.fn.scrollspy = function (n) {
        return this.each(function () {
            var o = t(this), a = o.data(i), s = "object" == typeof n && n;
            a || o.data(i, a = new e(this, s)), "string" == typeof n && a[n]()
        })
    }, t.fn.scrollspy.Constructor = e, t.fn.scrollspy.noConflict = function () {
        return t.fn.scrollspy = n, this
    }, t(window).on("load", function () {
        t('[data-spy="scroll"]').each(function () {
            var e = t(this);
            e.scrollspy(e.data())
        })
    })
}(jQuery), function (t, e) {
    "use strict";
    var i, n, o = "localStorage", a = "page_" + t.location.pathname + t.location.search, s = function () {
        this.slience = !0;
        try {
            o in t && t[o] && t[o].setItem && (this.enable = !0, i = t[o])
        } catch (s) {
        }
        this.enable || (n = {}, i = {
            getLength: function () {
                var t = 0;
                return e.each(n, function () {
                    t++
                }), t
            }, key: function (t) {
                var i, o = 0;
                return e.each(n, function (e) {
                    return o === t ? (i = e, !1) : void o++
                }), i
            }, removeItem: function (t) {
                delete n[t]
            }, getItem: function (t) {
                return n[t]
            }, setItem: function (t, e) {
                n[t] = e
            }, clear: function () {
                n = {}
            }
        }), this.storage = i, this.page = this.get(a, {})
    };
    s.prototype.pageSave = function () {
        if (e.isEmptyObject(this.page)) this.remove(a); else {
            var t, i = [];
            for (t in this.page) {
                var n = this.page[t];
                null === n && i.push(t)
            }
            for (t = i.length - 1; t >= 0; t--)delete this.page[i[t]];
            this.set(a, this.page)
        }
    }, s.prototype.pageRemove = function (t) {
        "undefined" != typeof this.page[t] && (this.page[t] = null, this.pageSave())
    }, s.prototype.pageClear = function () {
        this.page = {}, this.pageSave()
    }, s.prototype.pageGet = function (t, e) {
        var i = this.page[t];
        return void 0 === e || null !== i && void 0 !== i ? i : e
    }, s.prototype.pageSet = function (t, i) {
        e.isPlainObject(t) ? e.extend(!0, this.page, t) : this.page[this.serialize(t)] = i, this.pageSave()
    }, s.prototype.check = function () {
        if (!this.enable && !this.slience)throw new Error("Browser not support localStorage or enable status been set true.");
        return this.enable
    }, s.prototype.length = function () {
        return this.check() ? i.getLength ? i.getLength() : i.length : 0
    }, s.prototype.removeItem = function (t) {
        return i.removeItem(t), this
    }, s.prototype.remove = function (t) {
        return this.removeItem(t)
    }, s.prototype.getItem = function (t) {
        return i.getItem(t)
    }, s.prototype.get = function (t, e) {
        var i = this.deserialize(this.getItem(t));
        return "undefined" != typeof i && null !== i || "undefined" == typeof e ? i : e
    }, s.prototype.key = function (t) {
        return i.key(t)
    }, s.prototype.setItem = function (t, e) {
        return i.setItem(t, e), this
    }, s.prototype.set = function (t, e) {
        return void 0 === e ? this.remove(t) : (this.setItem(t, this.serialize(e)), this)
    }, s.prototype.clear = function () {
        return i.clear(), this
    }, s.prototype.forEach = function (t) {
        for (var e = this.length(), n = e - 1; n >= 0; n--) {
            var o = i.key(n);
            t(o, this.get(o))
        }
        return this
    }, s.prototype.getAll = function () {
        var t = {};
        return this.forEach(function (e, i) {
            t[e] = i
        }), t
    }, s.prototype.serialize = function (t) {
        return "string" == typeof t ? t : JSON.stringify(t)
    }, s.prototype.deserialize = function (t) {
        if ("string" == typeof t)try {
            return JSON.parse(t)
        } catch (e) {
            return t || void 0
        }
    }, e.zui({store: new s})
}(window, jQuery), function (t) {
    "use strict";
    var e = "zui.searchBox", i = function (e, n) {
        var o = this;
        o.name = name, o.$ = t(e), o.options = n = t.extend({}, i.DEFAULTS, o.$.data(), n);
        var a = o.$.is(n.inputSelector) ? o.$ : o.$.find(n.inputSelector);
        if (a.length) {
            var s = function () {
                o.changeTimer && (clearTimeout(o.changeTimer), o.changeTimer = null)
            }, r = function () {
                s();
                var t = o.getSearch();
                if (t !== o.lastValue) {
                    var e = "" === t;
                    a.toggleClass("empty", e), o.$.callComEvent(o, "onSearchChange", [t, e]), o.lastValue = t
                }
            };
            o.$input = a = a.first(), o.lastValue = o.getSearch(), a.on(n.listenEvent, function (t) {
                o.changeTimer = setTimeout(function () {
                    r()
                }, n.changeDelay)
            }).on("focus", function (t) {
                a.addClass("focus"), o.$.callComEvent(o, "onFocus", [t])
            }).on("blur", function (t) {
                a.removeClass("focus"), o.$.callComEvent(o, "onBlur", [t])
            }).on("keydown", function (t) {
                var e = 0, i = t.witch;
                27 === i && n.escToClear ? (this.setSearch("", !0), r(), e = 1) : 13 === i && n.onPressEnter && (r(), o.$.callComEvent(o, "onPressEnter", [t]));
                var a = o.$.callComEvent(o, "onKeyDown", [t]);
                a === !1 && (e = 1), e && t.preventDefault()
            }), o.$.on("click", ".search-clear-btn", function (t) {
                o.setSearch("", !0), r(), t.preventDefault()
            }), r()
        } else console.error("ZUI: search box init error, cannot find search box input element.")
    };
    i.DEFAULTS = {
        inputSelector: 'input[type="search"],input[type="text"]',
        listenEvent: "change input paste",
        changeDelay: 500
    }, i.prototype.getSearch = function () {
        return this.$input && t.trim(this.$input.val())
    }, i.prototype.setSearch = function (t, e) {
        var i = this.$input;
        i && (i.val(t), e || i.trigger("change"))
    }, i.prototype.focus = function () {
        this.$input && this.$input.focus()
    }, t.fn.searchBox = function (n) {
        return this.each(function () {
            var o = t(this), a = o.data(e), s = "object" == typeof n && n;
            a || o.data(e, a = new i(this, s)), "string" == typeof n && a[n]()
        })
    }, i.NAME = e, t.fn.searchBox.Constructor = i
}(jQuery), function (t, e) {
    "use strict";
    var i = "zui.draggable", n = {container: "body", move: !0}, o = 0, a = function (e, i) {
        var a = this;
        a.$ = t(e), a.id = o++, a.options = t.extend({}, n, a.$.data(), i), a.init()
    };
    a.DEFAULTS = n, a.NAME = i, a.prototype.init = function () {
        var n, o, a, s, r, l = this, c = l.$, d = "before", p = "drag", h = "finish", u = "." + i + "." + l.id,
            f = "mousedown" + u, g = "mouseup" + u, m = "mousemove" + u, v = l.options, y = v.selector, b = v.handle,
            w = c, C = t.isFunction(v.move), $ = function (t) {
                var e = t.pageX, i = t.pageY;
                r = !0;
                var o = {left: e - a.x, top: i - a.y};
                w.removeClass("drag-ready").addClass("dragging"), v.move && (C ? v.move(o, w) : w.css(o)), v[p] && v[p]({
                    event: t,
                    element: w,
                    startOffset: a,
                    pos: o,
                    offset: {x: e - n.x, y: i - n.y},
                    smallOffset: {x: e - s.x, y: i - s.y}
                }), s.x = e, s.y = i, v.stopPropagation && t.stopPropagation()
            }, x = function (i) {
                if (t(e).off(u), !r)return void w.removeClass("drag-ready");
                var o = {left: i.pageX - a.x, top: i.pageY - a.y};
                w.removeClass("drag-ready dragging"), v.move && (C ? v.move(o, w) : w.css(o)), v[h] && v[h]({
                    event: i,
                    element: w,
                    startOffset: a,
                    pos: o,
                    offset: {x: i.pageX - n.x, y: i.pageY - n.y},
                    smallOffset: {x: i.pageX - s.x, y: i.pageY - s.y}
                }), i.preventDefault(), v.stopPropagation && i.stopPropagation()
            }, T = function (i) {
                var l = t.zui.getMouseButtonCode(v.mouseButton);
                if (!(l > -1 && i.button !== l)) {
                    var c = t(this);
                    if (y && (w = b ? c.closest(y) : c), v[d]) {
                        var p = v[d]({event: i, element: w});
                        if (p === !1)return
                    }
                    var h = t(v.container), u = w.offset();
                    o = h.offset(), n = {x: i.pageX, y: i.pageY}, a = {
                        x: i.pageX - u.left + o.left,
                        y: i.pageY - u.top + o.top
                    }, s = t.extend({}, n), r = !1, w.addClass("drag-ready"), i.preventDefault(), v.stopPropagation && i.stopPropagation(), t(e).on(m, $).on(g, x)
                }
            };
        b ? c.on(f, b, T) : y ? c.on(f, y, T) : c.on(f, T)
    }, a.prototype.destroy = function () {
        var n = "." + i + "." + this.id;
        this.$.off(n), t(e).off(n), this.$.data(i, null)
    }, t.fn.draggable = function (e) {
        return this.each(function () {
            var n = t(this), o = n.data(i), s = "object" == typeof e && e;
            o || n.data(i, o = new a(this, s)), "string" == typeof e && o[e]()
        })
    }, t.fn.draggable.Constructor = a
}(jQuery, document), function (t, e, i) {
    "use strict";
    var n = "zui.droppable", o = {target: ".droppable-target", deviation: 5, sensorOffsetX: 0, sensorOffsetY: 0}, a = 0,
        s = function (e, i) {
            var n = this;
            n.id = a++, n.$ = t(e), n.options = t.extend({}, o, n.$.data(), i), n.init()
        };
    s.DEFAULTS = o, s.NAME = n, s.prototype.trigger = function (e, i) {
        return t.zui.callEvent(this.options[e], i, this)
    }, s.prototype.init = function () {
        var o, a, s, r, l, c, d, p, h, u, f, g, m, v = this, y = v.$, b = v.options, w = b.deviation,
            C = "." + n + "." + v.id, $ = "mousedown" + C, x = "mouseup" + C, T = "mousemove" + C, k = b.selector,
            D = b.handle, E = b.flex, S = b.container, z = b.canMoveHere, P = y, I = !1,
            M = S ? t(b.container).first() : k ? y : t("body"), j = function (e) {
                if (I && (f = {left: e.pageX, top: e.pageY}, !(i.abs(f.left - p.left) < w && i.abs(f.top - p.top) < w))) {
                    if (null === s) {
                        var n = M.css("position");
                        "absolute" != n && "relative" != n && "fixed" != n && (c = n, M.css("position", "relative")), s = P.clone().removeClass("drag-from").addClass("drag-shadow").css({
                            position: "absolute",
                            width: P.outerWidth(),
                            transition: "none"
                        }).appendTo(M), P.addClass("dragging"), v.trigger("start", {event: e, element: P})
                    }
                    var d = {left: f.left - u.left, top: f.top - u.top}, m = {left: d.left - h.left, top: d.top - h.top};
                    s.css(m), t.extend(g, f);
                    var y = !1;
                    r = !1, E || o.removeClass("drop-to");
                    var C = null;
                    if (o.each(function () {
                            var e = t(this), i = e.offset(), n = e.outerWidth(), o = e.outerHeight(),
                                a = i.left + b.sensorOffsetX, s = i.top + b.sensorOffsetY;
                            if (f.left > a && f.top > s && f.left < a + n && f.top < s + o && (C && C.removeClass("drop-to"), C = e, !b.nested))return !1
                        }), C) {
                        r = !0;
                        var $ = C.data("id");
                        P.data("id") != $ && (l = !1), (null === a || a.data("id") !== $ && !l) && (y = !0), a = C, E && o.removeClass("drop-to"), a.addClass("drop-to")
                    }
                    E ? null !== a && a.length && (r = !0) : (P.toggleClass("drop-in", r), s.toggleClass("drop-in", r)), z && z(P, a) === !1 || v.trigger("drag", {
                        event: e,
                        isIn: r,
                        target: a,
                        element: P,
                        isNew: y,
                        selfTarget: l,
                        clickOffset: u,
                        offset: d,
                        position: {left: d.left - h.left, top: d.top - h.top},
                        mouseOffset: f
                    }), e.preventDefault()
                }
            }, L = function (i) {
                if (t(e).off(C), clearTimeout(m), I) {
                    if (I = !1, c && M.css("position", c), null === s)return P.removeClass("drag-from"), void v.trigger("always", {
                        event: i,
                        cancel: !0
                    });
                    r || (a = null);
                    var n = !0;
                    f = i ? {left: i.pageX, top: i.pageY} : g;
                    var d = {left: f.left - u.left, top: f.top - u.top}, p = {left: f.left - g.left, top: f.top - g.top};
                    g.left = f.left, g.top = f.top;
                    var y = {
                        event: i,
                        isIn: r,
                        target: a,
                        element: P,
                        isNew: !l && null !== a,
                        selfTarget: l,
                        offset: d,
                        mouseOffset: f,
                        position: {left: d.left - h.left, top: d.top - h.top},
                        lastMouseOffset: g,
                        moveOffset: p
                    };
                    n = v.trigger("beforeDrop", y), n && r && v.trigger("drop", y), o.removeClass("drop-to"), P.removeClass("dragging").removeClass("drag-from"), s.remove(), s = null, v.trigger("finish", y), v.trigger("always", y), i && i.preventDefault()
                }
            }, O = function (i) {
                var n = t.zui.getMouseButtonCode(b.mouseButton);
                if (!(n > -1 && i.button !== n)) {
                    var f = t(this);
                    k && (P = D ? f.closest(k) : f), P.hasClass("drag-shadow") || b.before && b.before({
                        event: i,
                        element: P
                    }) === !1 || (I = !0, o = t.isFunction(b.target) ? b.target(y) : M.find(b.target), a = null, s = null, r = !1, l = !0, c = null, d = P.offset(), h = M.offset(), p = {
                        left: i.pageX,
                        top: i.pageY
                    }, g = t.extend({}, p), u = {
                        left: p.left - d.left,
                        top: p.top - d.top
                    }, P.addClass("drag-from"), t(e).on(T, j).on(x, L), m = setTimeout(function () {
                        t(e).on($, L)
                    }, 10), i.preventDefault())
                }
            };
        D ? y.on($, D, O) : k ? y.on($, k, O) : y.on($, O)
    }, s.prototype.destroy = function () {
        var i = "." + n + "." + this.id;
        this.$.off(i), t(e).off(i), this.$.data(n, null)
    }, s.prototype.reset = function () {
        this.destroy(), this.init()
    }, t.fn.droppable = function (e) {
        return this.each(function () {
            var i = t(this), o = i.data(n), a = "object" == typeof e && e;
            o || i.data(n, o = new s(this, a)), "string" == typeof e && o[e]()
        })
    }, t.fn.droppable.Constructor = s
}(jQuery, document, Math), +function (t) {
    "use strict";
    function e(e, o, a) {
        return this.each(function () {
            var s = t(this), r = s.data(i), l = t.extend({}, n.DEFAULTS, s.data(), "object" == typeof e && e);
            r || s.data(i, r = new n(this, l)), "string" == typeof e ? r[e](o, a) : l.show && r.show(o, a)
        })
    }

    var i = "zui.modal", n = function (e, n) {
        var o = this;
        o.options = n, o.$body = t(document.body), o.$element = t(e), o.$backdrop = o.isShown = null, o.scrollbarWidth = 0, void 0 === n.moveable && (o.options.moveable = o.$element.hasClass("modal-moveable")), n.remote && o.$element.find(".modal-content").load(n.remote, function () {
            o.$element.trigger("loaded." + i)
        })
    };
    n.VERSION = "3.2.0", n.TRANSITION_DURATION = 300, n.BACKDROP_TRANSITION_DURATION = 150, n.DEFAULTS = {
        backdrop: !0,
        keyboard: !0,
        show: !0,
        position: "fit"
    };
    var o = function (e, i) {
        var n = t(window);
        i.left = Math.max(0, Math.min(i.left, n.width() - e.outerWidth())), i.top = Math.max(0, Math.min(i.top, n.height() - e.outerHeight())), e.css(i)
    };
    n.prototype.toggle = function (t, e) {
        return this.isShown ? this.hide() : this.show(t, e)
    }, n.prototype.ajustPosition = function (e) {
        var n = this, a = n.options;
        if ("undefined" == typeof e && (e = a.position), "undefined" != typeof e) {
            var s = n.$element.find(".modal-dialog"), r = Math.max(0, (t(window).height() - s.outerHeight()) / 2),
                l = "fit" == e ? 2 * r / 3 : "center" == e ? r : e;
            if (s.hasClass("modal-moveable")) {
                var c = null, d = a.rememberPos;
                d && (d === !0 ? c = n.$element.data("modal-pos") : t.zui.store && (c = t.zui.store.pageGet(i + ".rememberPos." + d))), c || (c = {
                    left: Math.max(0, (t(window).width() - s.outerWidth()) / 2),
                    top: l
                }), "inside" === a.moveable ? o(s, c) : s.css(c)
            } else s.css("margin-top", l)
        }
    }, n.prototype.setMoveale = function () {
        t.fn.draggable || console.error("Moveable modal requires draggable.js.");
        var e = this, n = e.options, a = e.$element.find(".modal-dialog").removeClass("modal-dragged");
        a.toggleClass("modal-moveable", !!n.moveable), e.$element.data("modal-moveable-setup") || a.draggable({
            container: e.$element,
            handle: ".modal-header",
            before: function () {
                a.css("margin-top", "").addClass("modal-dragged")
            },
            finish: function (o) {
                var a = n.rememberPos;
                a && (e.$element.data("modal-pos", o.pos), t.zui.store && a !== !0 && t.zui.store.pageSet(i + ".rememberPos." + a, o.pos))
            },
            move: "inside" !== n.moveable || function (t) {
                o(a, t)
            }
        })
    }, n.prototype.show = function (e, o) {
        var a = this, s = t.Event("show." + i, {relatedTarget: e});
        a.$element.trigger(s), a.isShown || s.isDefaultPrevented() || (a.isShown = !0, a.options.moveable && a.setMoveale(), a.checkScrollbar(), a.$body.addClass("modal-open"), a.setScrollbar(), a.escape(), a.$element.on("click.dismiss." + i, '[data-dismiss="modal"]', t.proxy(a.hide, a)), a.backdrop(function () {
            var s = t.support.transition && a.$element.hasClass("fade");
            a.$element.parent().length || a.$element.appendTo(a.$body), a.$element.show().scrollTop(0), s && a.$element[0].offsetWidth, a.$element.addClass("in").attr("aria-hidden", !1), a.ajustPosition(o), a.enforceFocus();
            var r = t.Event("shown." + i, {relatedTarget: e});
            s ? a.$element.find(".modal-dialog").one("bsTransitionEnd", function () {
                a.$element.trigger("focus").trigger(r)
            }).emulateTransitionEnd(n.TRANSITION_DURATION) : a.$element.trigger("focus").trigger(r)
        }))
    }, n.prototype.hide = function (e) {
        e && e.preventDefault(), e = t.Event("hide." + i), this.$element.trigger(e), this.isShown && !e.isDefaultPrevented() && (this.isShown = !1, this.$body.removeClass("modal-open"), this.resetScrollbar(), this.escape(), t(document).off("focusin." + i), this.$element.removeClass("in").attr("aria-hidden", !0).off("click.dismiss." + i), t.support.transition && this.$element.hasClass("fade") ? this.$element.one("bsTransitionEnd", t.proxy(this.hideModal, this)).emulateTransitionEnd(n.TRANSITION_DURATION) : this.hideModal())
    }, n.prototype.enforceFocus = function () {
        t(document).off("focusin." + i).on("focusin." + i, t.proxy(function (t) {
            this.$element[0] === t.target || this.$element.has(t.target).length || this.$element.trigger("focus")
        }, this))
    }, n.prototype.escape = function () {
        this.isShown && this.options.keyboard ? t(document).on("keydown.dismiss." + i, t.proxy(function (e) {
            if (27 == e.which) {
                var n = t.Event("escaping." + i), o = this.$element.triggerHandler(n, "esc");
                if (void 0 != o && !o)return;
                this.hide()
            }
        }, this)) : this.isShown || t(document).off("keydown.dismiss." + i)
    }, n.prototype.hideModal = function () {
        var t = this;
        this.$element.hide(), this.backdrop(function () {
            t.$element.trigger("hidden." + i)
        })
    }, n.prototype.removeBackdrop = function () {
        this.$backdrop && this.$backdrop.remove(), this.$backdrop = null
    }, n.prototype.backdrop = function (e) {
        var o = this, a = this.$element.hasClass("fade") ? "fade" : "";
        if (this.isShown && this.options.backdrop) {
            var s = t.support.transition && a;
            if (this.$backdrop = t('<div class="modal-backdrop ' + a + '" />').appendTo(this.$body), this.$element.on("mousedown.dismiss." + i, t.proxy(function (t) {
                    t.target === t.currentTarget && ("static" == this.options.backdrop ? this.$element[0].focus.call(this.$element[0]) : this.hide.call(this))
                }, this)), s && this.$backdrop[0].offsetWidth, this.$backdrop.addClass("in"), !e)return;
            s ? this.$backdrop.one("bsTransitionEnd", e).emulateTransitionEnd(n.BACKDROP_TRANSITION_DURATION) : e()
        } else if (!this.isShown && this.$backdrop) {
            this.$backdrop.removeClass("in");
            var r = function () {
                o.removeBackdrop(), e && e()
            };
            t.support.transition && this.$element.hasClass("fade") ? this.$backdrop.one("bsTransitionEnd", r).emulateTransitionEnd(n.BACKDROP_TRANSITION_DURATION) : r()
        } else e && e()
    }, n.prototype.checkScrollbar = function () {
        document.body.clientWidth >= window.innerWidth || (this.scrollbarWidth = this.scrollbarWidth || this.measureScrollbar())
    }, n.prototype.setScrollbar = function () {
        var t = parseInt(this.$body.css("padding-right") || 0, 10);
        this.scrollbarWidth && this.$body.css("padding-right", t + this.scrollbarWidth)
    }, n.prototype.resetScrollbar = function () {
        this.$body.css("padding-right", "")
    }, n.prototype.measureScrollbar = function () {
        var t = document.createElement("div");
        t.className = "modal-scrollbar-measure", this.$body.append(t);
        var e = t.offsetWidth - t.clientWidth;
        return this.$body[0].removeChild(t), e
    };
    var a = t.fn.modal;
    t.fn.modal = e, t.fn.modal.Constructor = n, t.fn.modal.noConflict = function () {
        return t.fn.modal = a, this
    }, t(document).on("click." + i + ".data-api", '[data-toggle="modal"]', function (n) {
        var o = t(this), a = o.attr("href"), s = null;
        try {
            s = t(o.attr("data-target") || a && a.replace(/.*(?=#[^\s]+$)/, ""))
        } catch (r) {
            return
        }
        if (s.length) {
            var l = s.data(i) ? "toggle" : t.extend({remote: !/#/.test(a) && a}, s.data(), o.data());
            o.is("a") && n.preventDefault(), s.one("show." + i, function (t) {
                t.isDefaultPrevented() || s.one("hidden." + i, function () {
                    o.is(":visible") && o.trigger("focus")
                })
            }), e.call(s, l, this, o.data("position"))
        }
    })
}(jQuery), function (t, e) {
    "use strict";
    if (!t.fn.modal)throw new Error("Modal trigger requires modal.js");
    var i = "zui.modaltrigger", n = "ajax", o = ".zui.modal", a = "string", s = function (e, i) {
        e = t.extend({}, s.DEFAULTS, t.ModalTriggerDefaults, i ? i.data() : null, e), this.isShown, this.$trigger = i, this.options = e, this.id = t.zui.uuid()
    };
    s.DEFAULTS = {
        type: "custom",
        height: "auto",
        name: "triggerModal",
        fade: !0,
        position: "fit",
        showHeader: !0,
        delay: 0,
        backdrop: !0,
        keyboard: !0,
        waittime: 0,
        loadingIcon: "icon-spinner-indicator"
    }, s.prototype.init = function (s) {
        var r = this;
        if (s.url && (!s.type || s.type != n && "iframe" != s.type) && (s.type = n), s.remote) s.type = n, typeof s.remote === a && (s.url = s.remote); else if (s.iframe) s.type = "iframe", typeof s.iframe === a && (s.url = s.iframe); else if (s.custom && (s.type = "custom", typeof s.custom === a)) {
            var l;
            try {
                l = t(s.custom)
            } catch (c) {
            }
            l && l.length ? s.custom = l : t.isFunction(e[s.custom]) && (s.custom = e[s.custom])
        }
        var d = t("#" + s.name);
        d.length && (r.isShown || d.off(o), d.remove()), d = t('<div id="' + s.name + '" class="modal modal-trigger ' + (s.className || "") + '">' + ("string" == typeof s.loadingIcon && 0 === s.loadingIcon.indexOf("icon-") ? '<div class="icon icon-spin loader ' + s.loadingIcon + '"></div>' : s.loadingIcon) + '<div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button class="close" data-dismiss="modal">×</button><h4 class="modal-title"><i class="modal-icon"></i> <span class="modal-title-name"></span></h4></div><div class="modal-body"></div></div></div></div>').appendTo("body").data(i, r);
        var p = function (e, i) {
            var n = s[e];
            t.isFunction(n) && d.on(i + o, n)
        };
        p("onShow", "show"), p("shown", "shown"), p("onHide", "hide"), p("hidden", "hidden"), p("loaded", "loaded"), d.on("shown" + o, function () {
            r.isShown = !0
        }).on("hidden" + o, function () {
            r.isShown = !1
        }), this.$modal = d, this.$dialog = d.find(".modal-dialog"), s.mergeOptions && (this.options = s)
    }, s.prototype.show = function (o) {
        var s = t.extend({}, this.options, {url: this.$trigger ? this.$trigger.attr("href") || this.$trigger.attr("data-url") || this.$trigger.data("url") : this.options.url}, o);
        this.init(s);
        var r = this, l = this.$modal, c = this.$dialog, d = s.custom, p = c.find(".modal-body").css("padding", ""),
            h = c.find(".modal-header"), u = c.find(".modal-content");
        l.toggleClass("fade", s.fade).addClass(s.className).toggleClass("modal-loading", !this.isShown), c.toggleClass("modal-md", "md" === s.size).toggleClass("modal-sm", "sm" === s.size).toggleClass("modal-lg", "lg" === s.size).toggleClass("modal-fullscreen", "fullscreen" === s.size), h.toggle(s.showHeader), h.find(".modal-icon").attr("class", "modal-icon icon-" + s.icon), h.find(".modal-title-name").text(s.title || ""), s.size && "fullscreen" === s.size && (s.width = "", s.height = "");
        var f = function () {
            clearTimeout(this.resizeTask), this.resizeTask = setTimeout(function () {
                r.ajustPosition()
            }, 100)
        }, g = function (t, e) {
            return "undefined" == typeof t && (t = s.delay), setTimeout(function () {
                c = l.find(".modal-dialog"), s.width && "auto" != s.width && c.css("width", s.width), s.height && "auto" != s.height && (c.css("height", s.height), "iframe" === s.type && p.css("height", c.height() - h.outerHeight())), r.ajustPosition(s.position), l.removeClass("modal-loading"), "iframe" != s.type && c.off("resize." + i).on("resize." + i, f), e && e()
            }, t)
        };
        if ("custom" === s.type && d)if (t.isFunction(d)) {
            var m = d({modal: l, options: s, modalTrigger: r, ready: g});
            typeof m === a && (p.html(m), g())
        } else d instanceof t ? (p.html(t("<div>").append(d.clone()).html()), g()) : (p.html(d), g()); else if (s.url) {
            var v = function () {
                var t = l.callComEvent(r, "broken");
                t && (p.html(t), g())
            };
            if (l.attr("ref", s.url), "iframe" === s.type) {
                l.addClass("modal-iframe"), this.firstLoad = !0;
                var y = "iframe-" + s.name;
                h.detach(), p.detach(), u.empty().append(h).append(p), p.css("padding", 0).html('<iframe id="' + y + '" name="' + y + '" src="' + s.url + '" frameborder="no"  allowfullscreen="true" mozallowfullscreen="true" webkitallowfullscreen="true"  allowtransparency="true" scrolling="auto" style="width: 100%; height: 100%; left: 0px;"></iframe>'), s.waittime > 0 && (r.waitTimeout = g(s.waittime, v));
                var b = document.getElementById(y);
                b.onload = b.onreadystatechange = function () {
                    if (r.firstLoad && l.addClass("modal-loading"), !this.readyState || "complete" == this.readyState) {
                        r.firstLoad = !1, s.waittime > 0 && clearTimeout(r.waitTimeout);
                        try {
                            l.attr("ref", b.contentWindow.location.href);
                            var t = e.frames[y].$;
                            if (t && "auto" === s.height && "fullscreen" != s.size) {
                                var n = t("body").addClass("body-modal");
                                s.iframeBodyClass && n.addClass(s.iframeBodyClass);
                                var o = function (t) {
                                    l.removeClass("fade");
                                    var e = n.outerHeight();
                                    t === !0 && s.onlyIncreaseHeight && (e = Math.max(e, p.data("minModalHeight") || 0), p.data("minModalHeight", e)), p.css("height", e), s.fade && l.addClass("fade"), g()
                                };
                                l.callComEvent(r, "loaded", {
                                    modalType: "iframe",
                                    jQuery: t
                                }), setTimeout(o, 100), n.off("resize." + i).on("resize." + i, f)
                            } else g();
                            t.extend({closeModal: e.closeModal})
                        } catch (a) {
                            g()
                        }
                    }
                }
            } else t.ajax(t.extend({
                url: s.url, success: function (e) {
                    try {
                        var i = t(e);
                        i.hasClass("modal-dialog") ? c.replaceWith(i) : i.hasClass("modal-content") ? c.find(".modal-content").replaceWith(i) : p.wrapInner(i)
                    } catch (o) {
                        l.html(e)
                    }
                    l.callComEvent(r, "loaded", {modalType: n}), g()
                }, error: v
            }, s.ajaxOptions))
        }
        l.modal({
            show: "show",
            backdrop: s.backdrop,
            moveable: s.moveable,
            rememberPos: s.rememberPos,
            keyboard: s.keyboard
        })
    }, s.prototype.close = function (i, n) {
        (i || n) && this.$modal.on("hidden" + o, function () {
            t.isFunction(i) && i(), typeof n === a && ("this" === n ? e.location.reload() : e.location = n)
        }), this.$modal.modal("hide")
    }, s.prototype.toggle = function (t) {
        this.isShown ? this.close() : this.show(t)
    }, s.prototype.ajustPosition = function (t) {
        this.$modal.modal("ajustPosition", t || this.options.position)
    }, t.zui({ModalTrigger: s, modalTrigger: new s}), t.fn.modalTrigger = function (e, n) {
        return t(this).each(function () {
            var o = t(this), r = o.data(i), l = t.extend({
                title: o.attr("title") || o.text(),
                url: o.attr("href"),
                type: o.hasClass("iframe") ? "iframe" : ""
            }, o.data(), t.isPlainObject(e) && e);
            r || o.data(i, r = new s(l, o)), typeof e == a ? r[e](n) : l.show && r.show(n), o.on((l.trigger || "click") + ".toggle." + i, function (t) {
                r.toggle(l), o.is("a") && t.preventDefault()
            })
        })
    };
    var r = t.fn.modal;
    t.fn.modal = function (e, i) {
        return t(this).each(function () {
            var n = t(this);
            n.hasClass("modal") ? r.call(n, e, i) : n.modalTrigger(e, i)
        })
    };
    var l = function (e) {
        var i = typeof e;
        return "undefined" === i ? e = t(".modal.modal-trigger") : i === a && (e = t(e)), e && e instanceof t ? e : null
    }, c = function (e, n, o) {
        if (t.isFunction(e)) {
            var a = o;
            o = n, n = e, e = a
        }
        e = l(e), e && e.length && e.each(function () {
            t(this).data(i).close(n, o)
        })
    }, d = function (t, e) {
        e = l(e), e && e.length && e.modal("ajustPosition", t)
    };
    t.zui({
        closeModal: c,
        ajustModalPosition: d
    }), t(document).on("click." + i + ".data-api", '[data-toggle="modal"]', function (e) {
        var n = t(this), o = n.attr("href"), a = null;
        try {
            a = t(n.attr("data-target") || o && o.replace(/.*(?=#[^\s]+$)/, ""))
        } catch (s) {
        }
        a && a.length || (n.data(i) ? n.trigger(".toggle." + i) : n.modalTrigger({show: !0})), n.is("a") && e.preventDefault()
    })
}(window.jQuery, window), +function (t) {
    "use strict";
    var e = function (t, e) {
        this.type = null, this.options = null, this.enabled = null, this.timeout = null, this.hoverState = null, this.$element = null, this.init("tooltip", t, e)
    };
    e.DEFAULTS = {
        animation: !0,
        placement: "top",
        selector: !1,
        template: '<div class="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',
        trigger: "hover focus",
        title: "",
        delay: 0,
        html: !1,
        container: !1
    }, e.prototype.init = function (e, i, n) {
        this.enabled = !0, this.type = e, this.$element = t(i), this.options = this.getOptions(n);
        for (var o = this.options.trigger.split(" "), a = o.length; a--;) {
            var s = o[a];
            if ("click" == s) this.$element.on("click." + this.type, this.options.selector, t.proxy(this.toggle, this)); else if ("manual" != s) {
                var r = "hover" == s ? "mouseenter" : "focus", l = "hover" == s ? "mouseleave" : "blur";
                this.$element.on(r + "." + this.type, this.options.selector, t.proxy(this.enter, this)), this.$element.on(l + "." + this.type, this.options.selector, t.proxy(this.leave, this))
            }
        }
        this.options.selector ? this._options = t.extend({}, this.options, {
            trigger: "manual",
            selector: ""
        }) : this.fixTitle()
    }, e.prototype.getDefaults = function () {
        return e.DEFAULTS
    }, e.prototype.getOptions = function (e) {
        return e = t.extend({}, this.getDefaults(), this.$element.data(), e), e.delay && "number" == typeof e.delay && (e.delay = {
            show: e.delay,
            hide: e.delay
        }), e
    }, e.prototype.getDelegateOptions = function () {
        var e = {}, i = this.getDefaults();
        return this._options && t.each(this._options, function (t, n) {
            i[t] != n && (e[t] = n)
        }), e
    }, e.prototype.enter = function (e) {
        var i = e instanceof this.constructor ? e : t(e.currentTarget)[this.type](this.getDelegateOptions()).data("zui." + this.type);
        return clearTimeout(i.timeout), i.hoverState = "in", i.options.delay && i.options.delay.show ? void(i.timeout = setTimeout(function () {
            "in" == i.hoverState && i.show()
        }, i.options.delay.show)) : i.show()
    }, e.prototype.leave = function (e) {
        var i = e instanceof this.constructor ? e : t(e.currentTarget)[this.type](this.getDelegateOptions()).data("zui." + this.type);
        return clearTimeout(i.timeout), i.hoverState = "out", i.options.delay && i.options.delay.hide ? void(i.timeout = setTimeout(function () {
            "out" == i.hoverState && i.hide()
        }, i.options.delay.hide)) : i.hide()
    }, e.prototype.show = function (e) {
        var i = t.Event("show.zui." + this.type);
        if ((e || this.hasContent()) && this.enabled) {
            var n = this;
            if (n.$element.trigger(i), i.isDefaultPrevented())return;
            var o = n.tip();
            n.setContent(e), n.options.animation && o.addClass("fade");
            var a = "function" == typeof n.options.placement ? n.options.placement.call(n, o[0], n.$element[0]) : n.options.placement,
                s = /\s?auto?\s?/i, r = s.test(a);
            r && (a = a.replace(s, "") || "top"), o.detach().css({
                top: 0,
                left: 0,
                display: "block"
            }).addClass(a), n.options.container ? o.appendTo(n.options.container) : o.insertAfter(n.$element);
            var l = n.getPosition(), c = o[0].offsetWidth, d = o[0].offsetHeight;
            if (r) {
                var p = n.$element.parent(), h = a, u = document.documentElement.scrollTop || document.body.scrollTop,
                    f = "body" == n.options.container ? window.innerWidth : p.outerWidth(),
                    g = "body" == n.options.container ? window.innerHeight : p.outerHeight(),
                    m = "body" == n.options.container ? 0 : p.offset().left;
                a = "bottom" == a && l.top + l.height + d - u > g ? "top" : "top" == a && l.top - u - d < 0 ? "bottom" : "right" == a && l.right + c > f ? "left" : "left" == a && l.left - c < m ? "right" : a, o.removeClass(h).addClass(a)
            }
            var v = n.getCalculatedOffset(a, l, c, d);
            n.applyPlacement(v, a);
            var y = function () {
                var t = n.hoverState;
                n.$element.trigger("shown.zui." + n.type), n.hoverState = null, "out" == t && n.leave(n)
            };
            t.support.transition && n.$tip.hasClass("fade") ? o.one("bsTransitionEnd", y).emulateTransitionEnd(150) : y()
        }
    }, e.prototype.applyPlacement = function (t, e) {
        var i, n = this.tip(), o = n[0].offsetWidth, a = n[0].offsetHeight, s = parseInt(n.css("margin-top"), 10),
            r = parseInt(n.css("margin-left"), 10);
        isNaN(s) && (s = 0), isNaN(r) && (r = 0), t.top = t.top + s, t.left = t.left + r, n.offset(t).addClass("in");
        var l = n[0].offsetWidth, c = n[0].offsetHeight;
        if ("top" == e && c != a && (i = !0, t.top = t.top + a - c), /bottom|top/.test(e)) {
            var d = 0;
            t.left < 0 && (d = t.left * -2, t.left = 0, n.offset(t), l = n[0].offsetWidth, c = n[0].offsetHeight), this.replaceArrow(d - o + l, l, "left")
        } else this.replaceArrow(c - a, c, "top");
        i && n.offset(t)
    }, e.prototype.replaceArrow = function (t, e, i) {
        this.arrow().css(i, t ? 50 * (1 - t / e) + "%" : "")
    }, e.prototype.setContent = function (t) {
        var e = this.tip(), i = t || this.getTitle();
        this.options.tipId && e.attr("id", this.options.tipId), this.options.tipClass && e.addClass(this.options.tipClass), e.find(".tooltip-inner")[this.options.html ? "html" : "text"](i), e.removeClass("fade in top bottom left right")
    }, e.prototype.hide = function () {
        function e() {
            "in" != i.hoverState && n.detach()
        }

        var i = this, n = this.tip(), o = t.Event("hide.zui." + this.type);
        if (this.$element.trigger(o), !o.isDefaultPrevented())return n.removeClass("in"), t.support.transition && this.$tip.hasClass("fade") ? n.one(t.support.transition.end, e).emulateTransitionEnd(150) : e(), this.$element.trigger("hidden.zui." + this.type), this
    }, e.prototype.fixTitle = function () {
        var t = this.$element;
        (t.attr("title") || "string" != typeof t.attr("data-original-title")) && t.attr("data-original-title", t.attr("title") || "").attr("title", "")
    }, e.prototype.hasContent = function () {
        return this.getTitle()
    }, e.prototype.getPosition = function () {
        var e = this.$element[0];
        return t.extend({}, "function" == typeof e.getBoundingClientRect ? e.getBoundingClientRect() : {
            width: e.offsetWidth,
            height: e.offsetHeight
        }, this.$element.offset())
    }, e.prototype.getCalculatedOffset = function (t, e, i, n) {
        return "bottom" == t ? {
            top: e.top + e.height,
            left: e.left + e.width / 2 - i / 2
        } : "top" == t ? {
            top: e.top - n,
            left: e.left + e.width / 2 - i / 2
        } : "left" == t ? {top: e.top + e.height / 2 - n / 2, left: e.left - i} : {
            top: e.top + e.height / 2 - n / 2,
            left: e.left + e.width
        }
    }, e.prototype.getTitle = function () {
        var t, e = this.$element, i = this.options;
        return t = e.attr("data-original-title") || ("function" == typeof i.title ? i.title.call(e[0]) : i.title)
    }, e.prototype.tip = function () {
        return this.$tip = this.$tip || t(this.options.template)
    }, e.prototype.arrow = function () {
        return this.$arrow = this.$arrow || this.tip().find(".tooltip-arrow")
    }, e.prototype.validate = function () {
        this.$element[0].parentNode || (this.hide(), this.$element = null, this.options = null)
    }, e.prototype.enable = function () {
        this.enabled = !0
    }, e.prototype.disable = function () {
        this.enabled = !1
    }, e.prototype.toggleEnabled = function () {
        this.enabled = !this.enabled
    }, e.prototype.toggle = function (e) {
        var i = e ? t(e.currentTarget)[this.type](this.getDelegateOptions()).data("zui." + this.type) : this;
        i.tip().hasClass("in") ? i.leave(i) : i.enter(i)
    }, e.prototype.destroy = function () {
        this.hide().$element.off("." + this.type).removeData("zui." + this.type)
    };
    var i = t.fn.tooltip;
    t.fn.tooltip = function (i, n) {
        return this.each(function () {
            var o = t(this), a = o.data("zui.tooltip"), s = "object" == typeof i && i;
            a || o.data("zui.tooltip", a = new e(this, s)),
            "string" == typeof i && a[i](n)
        })
    }, t.fn.tooltip.Constructor = e, t.fn.tooltip.noConflict = function () {
        return t.fn.tooltip = i, this
    }
}(window.jQuery), +function (t) {
    "use strict";
    var e = function (t, e) {
        this.init("popover", t, e)
    };
    if (!t.fn.tooltip)throw new Error("Popover requires tooltip.js");
    e.DEFAULTS = t.extend({}, t.fn.tooltip.Constructor.DEFAULTS, {
        placement: "right",
        trigger: "click",
        content: "",
        template: '<div class="popover"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
    }), e.prototype = t.extend({}, t.fn.tooltip.Constructor.prototype), e.prototype.constructor = e, e.prototype.getDefaults = function () {
        return e.DEFAULTS
    }, e.prototype.setContent = function () {
        var t = this.tip(), e = this.getTarget();
        if (e)return e.find(".arrow").length < 1 && t.addClass("no-arrow"), void t.html(e.html());
        var i = this.getTitle(), n = this.getContent();
        t.find(".popover-title")[this.options.html ? "html" : "text"](i), t.find(".popover-content")[this.options.html ? "html" : "text"](n), t.removeClass("fade top bottom left right in"), this.options.tipId && t.attr("id", this.options.tipId), this.options.tipClass && t.addClass(this.options.tipClass), t.find(".popover-title").html() || t.find(".popover-title").hide()
    }, e.prototype.hasContent = function () {
        return this.getTarget() || this.getTitle() || this.getContent()
    }, e.prototype.getContent = function () {
        var t = this.$element, e = this.options;
        return t.attr("data-content") || ("function" == typeof e.content ? e.content.call(t[0]) : e.content)
    }, e.prototype.getTarget = function () {
        var e = this.$element, i = this.options,
            n = e.attr("data-target") || ("function" == typeof i.target ? i.target.call(e[0]) : i.target);
        return !!n && ("$next" == n ? e.next(".popover") : t(n))
    }, e.prototype.arrow = function () {
        return this.$arrow = this.$arrow || this.tip().find(".arrow")
    }, e.prototype.tip = function () {
        return this.$tip || (this.$tip = t(this.options.template)), this.$tip
    };
    var i = t.fn.popover;
    t.fn.popover = function (i) {
        return this.each(function () {
            var n = t(this), o = n.data("zui.popover"), a = "object" == typeof i && i;
            o || n.data("zui.popover", o = new e(this, a)), "string" == typeof i && o[i]()
        })
    }, t.fn.popover.Constructor = e, t.fn.popover.noConflict = function () {
        return t.fn.popover = i, this
    }
}(window.jQuery), +function (t) {
    "use strict";
    function e() {
        t(o).remove(), t(a).each(function (e) {
            var o = i(t(this));
            o.hasClass("open") && (o.trigger(e = t.Event("hide." + n)), e.isDefaultPrevented() || o.removeClass("open").trigger("hidden." + n))
        })
    }

    function i(e) {
        var i = e.attr("data-target");
        i || (i = e.attr("href"), i = i && /#/.test(i) && i.replace(/.*(?=#[^\s]*$)/, ""));
        var n;
        try {
            n = i && t(i)
        } catch (o) {
        }
        return n && n.length ? n : e.parent()
    }

    var n = "zui.dropdown", o = ".dropdown-backdrop", a = "[data-toggle=dropdown]", s = function (e) {
        t(e).on("click." + n, this.toggle)
    };
    s.prototype.toggle = function (o) {
        var a = t(this);
        if (!a.is(".disabled, :disabled")) {
            var s = i(a), r = s.hasClass("open");
            if (e(), !r) {
                if ("ontouchstart" in document.documentElement && !s.closest(".navbar-nav").length && t('<div class="dropdown-backdrop"/>').insertAfter(t(this)).on("click", e), s.trigger(o = t.Event("show." + n)), o.isDefaultPrevented())return;
                s.toggleClass("open").trigger("shown." + n), a.focus()
            }
            return !1
        }
    }, s.prototype.keydown = function (e) {
        if (/(38|40|27)/.test(e.keyCode)) {
            var n = t(this);
            if (e.preventDefault(), e.stopPropagation(), !n.is(".disabled, :disabled")) {
                var o = i(n), s = o.hasClass("open");
                if (!s || s && 27 == e.keyCode)return 27 == e.which && o.find(a).focus(), n.click();
                var r = t("[role=menu] li:not(.divider):visible a", o);
                if (r.length) {
                    var l = r.index(r.filter(":focus"));
                    38 == e.keyCode && l > 0 && l--, 40 == e.keyCode && l < r.length - 1 && l++, ~l || (l = 0), r.eq(l).focus()
                }
            }
        }
    };
    var r = t.fn.dropdown;
    t.fn.dropdown = function (e) {
        return this.each(function () {
            var i = t(this), n = i.data("dropdown");
            n || i.data("dropdown", n = new s(this)), "string" == typeof e && n[e].call(i)
        })
    }, t.fn.dropdown.Constructor = s, t.fn.dropdown.noConflict = function () {
        return t.fn.dropdown = r, this
    };
    var l = n + ".data-api";
    t(document).on("click." + l, e).on("click." + l, ".dropdown form", function (t) {
        t.stopPropagation()
    }).on("click." + l, a, s.prototype.toggle).on("keydown." + l, a + ", [role=menu]", s.prototype.keydown)
}(window.jQuery), function (t, e) {
    "use strict";
    var i = "zui.contextmenu", n = {duration: 200}, o = !1, a = {}, s = "zui-contextmenu-" + t.zui.uuid(), r = 0, l = 0,
        c = function () {
            return t(document).off("mousemove." + i).on("mousemove." + i, function (t) {
                r = t.clientX, l = t.clientY
            }), a
        }, d = function (e, i) {
            if ("string" == typeof e && (e = "seperator" === e || "divider" === e || "-" === e || "|" === e ? {type: "seperator"} : {
                    label: e,
                    id: i
                }), "seperator" === e.type || "divider" === e.type)return t('<li class="divider"></li>');
            var n = t("<a/>").attr({href: e.url || "###", "class": e.className, style: e.style}).data("item", e);
            return e.html ? n.html(e.html === !0 ? e.label || e.text : e.html) : n.text(e.label || e.text), e.onClick && n.on("click", e.onClick), t("<li />").toggleClass("disabled", e.disabled === !0).append(n)
        }, p = null, h = function (e, i) {
            "function" == typeof e && (i = e, e = null), p && (clearTimeout(p), p = null);
            var n = t("#" + s);
            if (n.length) {
                var o = n.data("options");
                if (!e || o.id === e) {
                    var r = function () {
                        n.hide(), o.onHidden && o.onHidden(), i && i()
                    };
                    o.onHide && o.onHide();
                    var l = o.animation;
                    n.removeClass("in"), l ? p = setTimeout(r, o.duration) : r()
                }
            }
            return a
        }, u = function (c, u, f) {
            t.isPlainObject(c) && (f = u, u = c, c = u.items), o = !0, u = t.extend({}, n, u);
            var g = u.x, m = u.y;
            g === e && (g = (u.event || u).clientX), g === e && (g = r), m === e && (m = (u.event || u).clientY), m === e && (m = l);
            var v = t("#" + s);
            v.length || (v = t('<div style="display: none; position: fixed; z-index: 2000;" class="contextmenu" id="' + s + '"><ul class="dropdown-menu contextmenu-menu"></ul></div>').appendTo("body"));
            var y = v.find(".contextmenu-menu").off("click." + i).on("click." + i, "a", function (e) {
                var i = t(this), n = u.onClickItem && u.onClickItem(i.data("item"), i, e);
                n !== !1 && h()
            }).empty();
            v.hide().attr("class", "contextmenu");
            var b = u.itemCreator || d, w = typeof c;
            "string" === w ? c = c.split(",") : "function" === w && (c = c(u)), t.each(c, function (t, e) {
                y.append(b(e, t, u))
            });
            var C = u.animation, $ = u.duration;
            C === !0 && (u.animation = C = "fade"), p && (clearTimeout(p), p = null);
            var x = function () {
                v.addClass("in"), u.onShown && u.onShown(), f && f()
            };
            u.onShow && u.onShow(), v.data("options", {
                animation: C,
                onHide: u.onHide,
                onHidden: u.onHidden,
                id: u.id,
                duration: $
            });
            var T = t(window);
            return g = Math.max(0, Math.min(g, T.width() - y.outerWidth())), m = Math.max(0, Math.min(m, T.height() - y.outerHeight())), v.css({
                left: g,
                top: m
            }), C ? (v.addClass("open").addClass(C).show(), p = setTimeout(function () {
                x(), o = !1
            }, u.duration)) : (v.addClass("open").show(), x(), p = setTimeout(function () {
                o = !1
            }, 200)), a
        };
    t(document).on("click", function (e) {
        o || t(e.target).closest(".contextmenu").length || h()
    }), t.extend(a, {NAME: i, DEFAULTS: n, show: u, hide: h, listenMouse: c}), t.zui({ContextMenu: a});
    var f = function (e, n) {
        var o = this;
        o.name = i, o.$ = t(e), n = o.options = t.extend({trigger: "contextmenu"}, a.DEFAULTS, this.$.data(), n);
        var s = n.trigger;
        o.id = t.zui.uuid();
        var r = function (t) {
            if ("mousedown" !== t.type || 2 === t.button) {
                var e = {x: t.clientX, y: t.clientY, event: t};
                return n.itemsCreator && (e.items = n.itemsCreator.call(this, t)), o.show(e), t.preventDefault(), t.returnValue = !1, !1
            }
        }, l = s + "." + i;
        n.selector ? o.$.on(l, n.selector, r) : o.$.on(l, r)
    };
    f.prototype.destory = function () {
        that.$.off("." + i)
    }, f.prototype.hide = function (t) {
        a.hide(this.id, t)
    }, f.prototype.show = function (e, i) {
        e = t.extend({}, this.options, e), a.show(e, i)
    }, t.fn.contextmenu = function (e) {
        return this.each(function () {
            var n = t(this), o = n.data(i), a = "object" == typeof e && e;
            o || n.data(i, o = new f(this, a)), "string" == typeof e && o[e]()
        })
    }, t.fn.contextmenu.Constructor = f
}(jQuery, void 0), +function (t) {
    "use strict";
    var e = function (e, i) {
        this.$element = t(e), this.$indicators = this.$element.find(".carousel-indicators"), this.options = i, this.paused = this.sliding = this.interval = this.$active = this.$items = null, "hover" == this.options.pause && this.$element.on("mouseenter", t.proxy(this.pause, this)).on("mouseleave", t.proxy(this.cycle, this))
    };
    e.DEFAULTS = {interval: 5e3, pause: "hover", wrap: !0, touchable: !0}, e.prototype.touchable = function () {
        function e(e) {
            var e = e || window.event;
            e.originalEvent && (e = e.originalEvent);
            var a = t(this);
            switch (e.type) {
                case"touchstart":
                    n = e.touches[0].pageX, o = e.touches[0].pageY;
                    break;
                case"touchend":
                    var s = e.changedTouches[0].pageX - n, r = e.changedTouches[0].pageY - o;
                    if (Math.abs(s) > Math.abs(r)) i(a, s), Math.abs(s) > 10 && e.preventDefault(); else {
                        var l = t(window);
                        t("body,html").animate({scrollTop: l.scrollTop() - r}, 400)
                    }
            }
        }

        function i(t, e) {
            e > 10 ? a.prev() : e < -10 && a.next()
        }

        if (this.options.touchable) {
            this.$element.on("touchstart touchmove touchend", e);
            var n, o, a = this
        }
    }, e.prototype.cycle = function (e) {
        return e || (this.paused = !1), this.interval && clearInterval(this.interval), this.options.interval && !this.paused && (this.interval = setInterval(t.proxy(this.next, this), this.options.interval)), this
    }, e.prototype.getActiveIndex = function () {
        return this.$active = this.$element.find(".item.active"), this.$items = this.$active.parent().children(), this.$items.index(this.$active)
    }, e.prototype.to = function (e) {
        var i = this, n = this.getActiveIndex();
        if (!(e > this.$items.length - 1 || e < 0))return this.sliding ? this.$element.one("slid", function () {
            i.to(e)
        }) : n == e ? this.pause().cycle() : this.slide(e > n ? "next" : "prev", t(this.$items[e]))
    }, e.prototype.pause = function (e) {
        return e || (this.paused = !0), this.$element.find(".next, .prev").length && t.support.transition.end && (this.$element.trigger(t.support.transition.end), this.cycle(!0)), this.interval = clearInterval(this.interval), this
    }, e.prototype.next = function () {
        if (!this.sliding)return this.slide("next")
    }, e.prototype.prev = function () {
        if (!this.sliding)return this.slide("prev")
    }, e.prototype.slide = function (e, i) {
        var n = this.$element.find(".item.active"), o = i || n[e](), a = this.interval,
            s = "next" == e ? "left" : "right", r = "next" == e ? "first" : "last", l = this;
        if (!o.length) {
            if (!this.options.wrap)return;
            o = this.$element.find(".item")[r]()
        }
        this.sliding = !0, a && this.pause();
        var c = t.Event("slide.zui.carousel", {relatedTarget: o[0], direction: s});
        if (!o.hasClass("active")) {
            if (this.$indicators.length && (this.$indicators.find(".active").removeClass("active"), this.$element.one("slid", function () {
                    var e = t(l.$indicators.children()[l.getActiveIndex()]);
                    e && e.addClass("active")
                })), t.support.transition && this.$element.hasClass("slide")) {
                if (this.$element.trigger(c), c.isDefaultPrevented())return;
                o.addClass(e), o[0].offsetWidth, n.addClass(s), o.addClass(s), n.one(t.support.transition.end, function () {
                    o.removeClass([e, s].join(" ")).addClass("active"), n.removeClass(["active", s].join(" ")), l.sliding = !1, setTimeout(function () {
                        l.$element.trigger("slid")
                    }, 0)
                }).emulateTransitionEnd(600)
            } else {
                if (this.$element.trigger(c), c.isDefaultPrevented())return;
                n.removeClass("active"), o.addClass("active"), this.sliding = !1, this.$element.trigger("slid")
            }
            return a && this.cycle(), this
        }
    };
    var i = t.fn.carousel;
    t.fn.carousel = function (i) {
        return this.each(function () {
            var n = t(this), o = n.data("zui.carousel"),
                a = t.extend({}, e.DEFAULTS, n.data(), "object" == typeof i && i),
                s = "string" == typeof i ? i : a.slide;
            o || n.data("zui.carousel", o = new e(this, a)), "number" == typeof i ? o.to(i) : s ? o[s]() : a.interval && o.pause().cycle(), a.touchable && o.touchable()
        })
    }, t.fn.carousel.Constructor = e, t.fn.carousel.noConflict = function () {
        return t.fn.carousel = i, this
    }, t(document).on("click.zui.carousel.data-api", "[data-slide], [data-slide-to]", function (e) {
        var i, n = t(this), o = t(n.attr("data-target") || (i = n.attr("href")) && i.replace(/.*(?=#[^\s]+$)/, "")),
            a = t.extend({}, o.data(), n.data()), s = n.attr("data-slide-to");
        s && (a.interval = !1), o.carousel(a), (s = n.attr("data-slide-to")) && o.data("zui.carousel").to(s), e.preventDefault()
    }), t(window).on("load", function () {
        t('[data-ride="carousel"]').each(function () {
            var e = t(this);
            e.carousel(e.data())
        })
    })
}(window.jQuery), /*! TangBin: image.ready.js http://www.planeart.cn/?p=1121 */
    function (t) {
        "use strict";
        t.zui.imgReady = function () {
            var t = [], e = null, i = function () {
                for (var e = 0; e < t.length; e++)t[e].end ? t.splice(e--, 1) : t[e]();
                !t.length && n()
            }, n = function () {
                clearInterval(e), e = null
            };
            return function (n, o, a, s) {
                var r, l, c, d, p, h = new Image;
                return h.src = n, h.complete ? (o.call(h), void(a && a.call(h))) : (l = h.width, c = h.height, h.onerror = function () {
                    s && s.call(h), r.end = !0, h = h.onload = h.onerror = null
                }, r = function () {
                    d = h.width, p = h.height, (d !== l || p !== c || d * p > 1024) && (o.call(h), r.end = !0)
                }, r(), h.onload = function () {
                    !r.end && r(), a && a.call(h), h = h.onload = h.onerror = null
                }, void(r.end || (t.push(r), null === e && (e = setInterval(i, 40)))))
            }
        }()
    }(jQuery), function (t, e, i) {
    "use strict";
    if (!t.fn.modalTrigger)throw new Error("modal & modalTrigger requires for lightbox");
    if (!t.zui.imgReady)throw new Error("imgReady requires for lightbox");
    var n = function (e, i) {
        this.$ = t(e), this.options = this.getOptions(i), this.init()
    };
    n.DEFAULTS = {modalTeamplate: '<div class="icon-spinner icon-spin loader"></div><div class="modal-dialog"><button class="close" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i></button><button class="controller prev"><i class="icon icon-chevron-left"></i></button><button class="controller next"><i class="icon icon-chevron-right"></i></button><img class="lightbox-img" src="{image}" alt="" data-dismiss="modal" /><div class="caption"><div class="content">{caption}<div></div></div>'}, n.prototype.getOptions = function (e) {
        var i = "image";
        return e = t.extend({}, n.DEFAULTS, this.$.data(), e), e[i] || (e[i] = this.$.attr("src") || this.$.attr("href") || this.$.find("img").attr("src"), this.$.data(i, e[i])), e
    }, n.prototype.init = function () {
        this.bindEvents()
    }, n.prototype.initGroups = function () {
        var e = this.$.data("groups");
        e || (e = t('[data-toggle="lightbox"][data-group="' + this.options.group + '"], [data-lightbox-group="' + this.options.group + '"]'), this.$.data("groups", e), e.each(function (e) {
            t(this).attr("data-group-index", e)
        })), this.groups = e, this.groupIndex = parseInt(this.$.data("group-index"))
    }, n.prototype.setImage = function (t, e) {
        void 0 !== t && (this.options.image = t), void 0 !== e && (this.options.caption = e)
    }, n.prototype.show = function (t, e) {
        this.setImage(t, e), this.$.triggerHandler("click")
    }, n.prototype.bindEvents = function () {
        var n = this.$, o = this, a = this.options;
        return !!a.image && void n.modalTrigger({
                type: "custom",
                name: "lightboxModal",
                position: "center",
                custom: function (n) {
                    o.initGroups();
                    var s = n.modal, r = o.groups, l = o.groupIndex;
                    s.addClass("modal-lightbox").html(a.modalTeamplate.format(a)).toggleClass("lightbox-with-caption", "string" == typeof a.caption).removeClass("lightbox-full").data("group-index", l);
                    var c = s.find(".modal-dialog"), d = t(e).width();
                    t.zui.imgReady(a.image, function () {
                        c.css({width: i.min(d, this.width)}), d < this.width + 30 && s.addClass("lightbox-full"), n.ready(200)
                    }), s.find(".prev").toggleClass("show", r.filter('[data-group-index="' + (l - 1) + '"]').length > 0), s.find(".next").toggleClass("show", r.filter('[data-group-index="' + (l + 1) + '"]').length > 0), s.find(".controller").click(function () {
                        var o = t(this), a = s.data("group-index") + (o.hasClass("prev") ? -1 : 1),
                            l = r.filter('[data-group-index="' + a + '"]');
                        if (l.length) {
                            var p = l.data("image"), h = l.data("caption");
                            s.addClass("modal-loading").data("group-index", a).toggleClass("lightbox-with-caption", "string" == typeof h).removeClass("lightbox-full"), s.find(".lightbox-img").attr("src", p), s.find(".caption > .content").text(h), d = t(e).width(), t.zui.imgReady(p, function () {
                                c.css({width: i.min(d, this.width)}), d < this.width + 30 && s.addClass("lightbox-full"), n.ready()
                            })
                        }
                        return s.find(".prev").toggleClass("show", r.filter('[data-group-index="' + (a - 1) + '"]').length > 0), s.find(".next").toggleClass("show", r.filter('[data-group-index="' + (a + 1) + '"]').length > 0), !1
                    })
                }
            })
    }, t.fn.lightbox = function (e) {
        var i = "group" + (new Date).getTime();
        return this.each(function () {
            var o = t(this), a = "object" == typeof e && e;
            "object" == typeof a && a.group ? o.attr("data-lightbox-group", a.group) : o.data("group") ? o.attr("data-lightbox-group", o.data("group")) : o.attr("data-lightbox-group", i), o.data("group", o.data("lightbox-group"));
            var s = o.data("zui.lightbox");
            s || o.data("zui.lightbox", s = new n(this, a)), "string" == typeof e && s[e]()
        })
    }, t.fn.lightbox.Constructor = n, t(function () {
        t('[data-toggle="lightbox"]').lightbox()
    })
}(jQuery, window, Math), function (t, e, i) {
    "use strict";
    var n = 0,
        o = '<div class="messager messager-{type} {placement}" style="display: none"><div class="messager-content"></div><div class="messager-actions"></div></div>',
        a = {type: "default", placement: "top", time: 4e3, parent: "body", icon: null, close: !0, fade: !0, scale: !0},
        s = {}, r = function (e, r) {
            t.isPlainObject(e) && (r = e, e = r.message);
            var l = this;
            r = l.options = t.extend({}, a, r), l.id = r.id || n++;
            var c = s[l.id];
            c && c.destroy(), s[l.id] = l, l.message = (r.icon ? '<i class="icon-' + r.icon + ' icon"></i> ' : "") + e, l.$ = t(o.format(r)).toggleClass("fade", r.fade).toggleClass("scale", r.scale).attr("id", "messager-" + l.id), r.cssClass && l.$.addClass(r.cssClass);
            var d = !1, p = l.$.find(".messager-actions"), h = function (e) {
                var n = t('<button type="button" class="action action-' + e.name + '"/>');
                "close" === e.name && n.addClass("close"), e.html !== i && n.html(e.html), e.icon !== i && n.append('<i class="action-icon icon-' + e.icon + '"/>'), e.text !== i && n.append('<span class="action-text">' + e.text + "</span>"), e.tooltip !== i && n.attr("title", e.tooltip).tooltip(), n.data("action", e), p.append(n)
            };
            r.actions && t.each(r.actions, function (t, e) {
                e.name === i && (e.name = t), "close" == e.name && (d = !0), h(e)
            }), !d && r.close && h({name: "close", html: "&times;"}), l.$.on("click", ".action", function (e) {
                var i, n = t(this).data("action");
                r.onAction && (i = r.onAction.call(this, n.name, n, l), i === !1) || t.isFunction(n.action) && (i = n.action.call(this, l), i === !1) || (l.hide(), e.stopPropagation())
            }), l.$.on("click", function (t) {
                if (r.onAction) {
                    var e = r.onAction.call(this, "content", null, l);
                    e === !0 && l.hide()
                }
            });
            var u = l.$.find(".messager-content").html(l.message);
            r.contentClass && u.addClass(r.contentClass), l.$.data("zui.messager", l), r.show && l.message !== i && l.show()
        };
    r.prototype.update = function (e, i) {
        var n = this, o = n.options;
        n.$.removeClass("messager-" + o.type), i && (o = t.extend(o, i)), n.$.addClass("messager-" + o.type), e && (n.message = (o.icon ? '<i class="icon-' + o.icon + ' icon"></i> ' : "") + e, n.$.find(".messager-content").html(n.message))
    }, r.prototype.show = function (n, o) {
        var a = this, s = this.options;
        if (t.isFunction(n)) {
            var r = o;
            o = n, r !== i && (n = r)
        }
        if (a.isShow)return void a.hide(function () {
            a.show(n, o)
        });
        a.hiding && (clearTimeout(a.hiding), a.hiding = null), a.update(n);
        var l = s.placement, c = t(s.parent), d = c.children(".messagers-holder." + l);
        if (d.length || (d = t("<div/>").attr("class", "messagers-holder " + l).appendTo(c)), d.append(a.$), "center" === l) {
            var p = t(e).height() - d.height();
            d.css("top", Math.max(-p, p / 2))
        }
        return a.$.show().addClass("in"), s.time && (a.hiding = setTimeout(function () {
            a.hide()
        }, s.time)), a.isShow = !0, o && o(), a
    }, r.prototype.hide = function (t, e) {
        t === !0 && (e = !0, t = null);
        var i = this;
        if (i.$.hasClass("in")) {
            i.$.removeClass("in");
            var n = function () {
                var e = i.$.parent();
                i.$.detach(), e.children().length || e.remove(), t && t(!0)
            };
            e ? n() : setTimeout(n, 200)
        } else t && t(!1);
        i.isShow = !1
    }, r.prototype.destroy = function () {
        var t = this;
        t.hide(function () {
            t.$.remove(), t.$ = null
        }, !0), delete s[t.id]
    }, r.all = s;
    var l = function () {
        t(".messager").each(function () {
            var e = t(this).data("zui.messager");
            e && e.hide && e.hide(!0)
        })
    }, c = function (e, n) {
        "string" == typeof n && (n = {type: n}), n = t.extend({}, n), n.id === i && l();
        var o = s[n.id] || new r(e, n);
        return o.show(), o
    }, d = function (t) {
        return "string" == typeof t ? {placement: t} : t
    }, p = {show: c, hide: l};
    t.each({
        primary: 0,
        success: "ok-sign",
        info: "info-sign",
        warning: "warning-sign",
        danger: "exclamation-sign",
        important: 0,
        special: 0
    }, function (e, i) {
        p[e] = function (n, o) {
            return c(n, t.extend({type: e, icon: i || null}, d(o)))
        }
    }), t.zui({Messager: r, showMessager: c, messager: p})
}(jQuery, window, void 0), function (t, e, i, n) {
    "use strict";
    function o(t) {
        if (t = t.toLowerCase(), t && d.test(t)) {
            var e;
            if (4 === t.length) {
                var i = "#";
                for (e = 1; e < 4; e += 1)i += t.slice(e, e + 1).concat(t.slice(e, e + 1));
                t = i
            }
            var n = [];
            for (e = 1; e < 7; e += 2)n.push(b("0x" + t.slice(e, e + 2)));
            return {r: n[0], g: n[1], b: n[2], a: 1}
        }
        throw new Error("Wrong hex string! (hex: " + t + ")")
    }

    function a(e) {
        return typeof e === f && ("transparent" === e.toLowerCase() || m[e.toLowerCase()] || d.test(t.trim(e.toLowerCase())))
    }

    function s(t) {
        function e(t) {
            return t = t < 0 ? t + 1 : t > 1 ? t - 1 : t, 6 * t < 1 ? r + (s - r) * t * 6 : 2 * t < 1 ? s : 3 * t < 2 ? r + (s - r) * (2 / 3 - t) * 6 : r
        }

        var i = t.h, n = t.s, o = t.l, a = t.a;
        i = c(i) % h / h, n = l(c(n)), o = l(c(o)), a = l(c(a));
        var s = o <= .5 ? o * (n + 1) : o + n - o * n, r = 2 * o - s,
            d = {r: e(i + 1 / 3) * p, g: e(i) * p, b: e(i - 1 / 3) * p, a: a};
        return d
    }

    function r(t, i, n) {
        return v(n) && (n = 0), v(i) && (i = p), e.min(e.max(t, n), i)
    }

    function l(t, e) {
        return r(t, e)
    }

    function c(t) {
        return "number" == typeof t ? t : parseFloat(t)
    }

    var d = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/, p = 255, h = 360, u = 100, f = "string", g = "object", m = {
        aliceblue: "#f0f8ff",
        antiquewhite: "#faebd7",
        aqua: "#00ffff",
        aquamarine: "#7fffd4",
        azure: "#f0ffff",
        beige: "#f5f5dc",
        bisque: "#ffe4c4",
        black: "#000000",
        blanchedalmond: "#ffebcd",
        blue: "#0000ff",
        blueviolet: "#8a2be2",
        brown: "#a52a2a",
        burlywood: "#deb887",
        cadetblue: "#5f9ea0",
        chartreuse: "#7fff00",
        chocolate: "#d2691e",
        coral: "#ff7f50",
        cornflowerblue: "#6495ed",
        cornsilk: "#fff8dc",
        crimson: "#dc143c",
        cyan: "#00ffff",
        darkblue: "#00008b",
        darkcyan: "#008b8b",
        darkgoldenrod: "#b8860b",
        darkgray: "#a9a9a9",
        darkgreen: "#006400",
        darkkhaki: "#bdb76b",
        darkmagenta: "#8b008b",
        darkolivegreen: "#556b2f",
        darkorange: "#ff8c00",
        darkorchid: "#9932cc",
        darkred: "#8b0000",
        darksalmon: "#e9967a",
        darkseagreen: "#8fbc8f",
        darkslateblue: "#483d8b",
        darkslategray: "#2f4f4f",
        darkturquoise: "#00ced1",
        darkviolet: "#9400d3",
        deeppink: "#ff1493",
        deepskyblue: "#00bfff",
        dimgray: "#696969",
        dodgerblue: "#1e90ff",
        firebrick: "#b22222",
        floralwhite: "#fffaf0",
        forestgreen: "#228b22",
        fuchsia: "#ff00ff",
        gainsboro: "#dcdcdc",
        ghostwhite: "#f8f8ff",
        gold: "#ffd700",
        goldenrod: "#daa520",
        gray: "#808080",
        green: "#008000",
        greenyellow: "#adff2f",
        honeydew: "#f0fff0",
        hotpink: "#ff69b4",
        indianred: "#cd5c5c",
        indigo: "#4b0082",
        ivory: "#fffff0",
        khaki: "#f0e68c",
        lavender: "#e6e6fa",
        lavenderblush: "#fff0f5",
        lawngreen: "#7cfc00",
        lemonchiffon: "#fffacd",
        lightblue: "#add8e6",
        lightcoral: "#f08080",
        lightcyan: "#e0ffff",
        lightgoldenrodyellow: "#fafad2",
        lightgray: "#d3d3d3",
        lightgreen: "#90ee90",
        lightpink: "#ffb6c1",
        lightsalmon: "#ffa07a",
        lightseagreen: "#20b2aa",
        lightskyblue: "#87cefa",
        lightslategray: "#778899",
        lightsteelblue: "#b0c4de",
        lightyellow: "#ffffe0",
        lime: "#00ff00",
        limegreen: "#32cd32",
        linen: "#faf0e6",
        magenta: "#ff00ff",
        maroon: "#800000",
        mediumaquamarine: "#66cdaa",
        mediumblue: "#0000cd",
        mediumorchid: "#ba55d3",
        mediumpurple: "#9370db",
        mediumseagreen: "#3cb371",
        mediumslateblue: "#7b68ee",
        mediumspringgreen: "#00fa9a",
        mediumturquoise: "#48d1cc",
        mediumvioletred: "#c71585",
        midnightblue: "#191970",
        mintcream: "#f5fffa",
        mistyrose: "#ffe4e1",
        moccasin: "#ffe4b5",
        navajowhite: "#ffdead",
        navy: "#000080",
        oldlace: "#fdf5e6",
        olive: "#808000",
        olivedrab: "#6b8e23",
        orange: "#ffa500",
        orangered: "#ff4500",
        orchid: "#da70d6",
        palegoldenrod: "#eee8aa",
        palegreen: "#98fb98",
        paleturquoise: "#afeeee",
        palevioletred: "#db7093",
        papayawhip: "#ffefd5",
        peachpuff: "#ffdab9",
        peru: "#cd853f",
        pink: "#ffc0cb",
        plum: "#dda0dd",
        powderblue: "#b0e0e6",
        purple: "#800080",
        red: "#ff0000",
        rosybrown: "#bc8f8f",
        royalblue: "#4169e1",
        saddlebrown: "#8b4513",
        salmon: "#fa8072",
        sandybrown: "#f4a460",
        seagreen: "#2e8b57",
        seashell: "#fff5ee",
        sienna: "#a0522d",
        silver: "#c0c0c0",
        skyblue: "#87ceeb",
        slateblue: "#6a5acd",
        slategray: "#708090",
        snow: "#fffafa",
        springgreen: "#00ff7f",
        steelblue: "#4682b4",
        tan: "#d2b48c",
        teal: "#008080",
        thistle: "#d8bfd8",
        tomato: "#ff6347",
        turquoise: "#40e0d0",
        violet: "#ee82ee",
        wheat: "#f5deb3",
        white: "#ffffff",
        whitesmoke: "#f5f5f5",
        yellow: "#ffff00",
        yellowgreen: "#9acd32"
    }, v = function (t) {
        return t === n
    }, y = function (t) {
        return !v(t)
    }, b = function (t) {
        return parseInt(t)
    }, w = function (t) {
        return b(l(c(t), p))
    }, C = function (t, e, i, n) {
        var a = this;
        if (a.r = a.g = a.b = 0, a.a = 1, y(n) && (a.a = l(c(n), 1)), y(t) && y(e) && y(i)) a.r = w(t), a.g = w(e), a.b = w(i); else if (y(t)) {
            var r = typeof t;
            if (r == f) t = t.toLowerCase(), "transparent" === t ? a.a = 0 : m[t] ? this.rgb(o(m[t])) : a.rgb(o(t)); else if ("number" == r && v(e)) a.r = a.g = a.b = w(t); else if (r == g && y(t.r)) a.r = w(t.r), y(t.g) && (a.g = w(t.g)), y(t.b) && (a.b = w(t.b)), y(t.a) && (a.a = l(c(t.a), 1)); else if (r == g && y(t.h)) {
                var d = {h: l(c(t.h), h), s: 1, l: 1, a: 1};
                y(t.s) && (d.s = l(c(t.s), 1)), y(t.l) && (d.l = l(c(t.l), 1)), y(t.a) && (d.a = l(c(t.a), 1)), a.rgb(s(d))
            }
        }
    };
    C.prototype.rgb = function (t) {
        var e = this;
        if (y(t)) {
            if (typeof t == g) y(t.r) && (e.r = w(t.r)), y(t.g) && (e.g = w(t.g)), y(t.b) && (e.b = w(t.b)), y(t.a) && (e.a = l(c(t.a), 1)); else {
                var i = b(c(t));
                e.r = i, e.g = i, e.b = i
            }
            return e
        }
        return {r: e.r, g: e.g, b: e.b, a: e.a}
    }, C.prototype.hue = function (t) {
        var e = this, i = e.toHsl();
        return v(t) ? i.h : (i.h = l(c(t), h), e.rgb(s(i)), e)
    }, C.prototype.darken = function (t) {
        var e = this, i = e.toHsl();
        return i.l -= t / u, i.l = l(i.l, 1), e.rgb(s(i)), e
    }, C.prototype.clone = function () {
        var t = this;
        return new C(t.r, t.g, t.b, t.a)
    }, C.prototype.lighten = function (t) {
        return this.darken(-t)
    }, C.prototype.fade = function (t) {
        return this.a = l(t / u, 1), this
    }, C.prototype.spin = function (t) {
        var e = this.toHsl(), i = (e.h + t) % h;
        return e.h = i < 0 ? h + i : i, this.rgb(s(e))
    }, C.prototype.toHsl = function () {
        var t, i, n = this, o = n.r / p, a = n.g / p, s = n.b / p, r = n.a, l = e.max(o, a, s), c = e.min(o, a, s),
            d = (l + c) / 2, u = l - c;
        if (l === c) t = i = 0; else {
            switch (i = d > .5 ? u / (2 - l - c) : u / (l + c), l) {
                case o:
                    t = (a - s) / u + (a < s ? 6 : 0);
                    break;
                case a:
                    t = (s - o) / u + 2;
                    break;
                case s:
                    t = (o - a) / u + 4
            }
            t /= 6
        }
        return {h: t * h, s: i, l: d, a: r}
    }, C.prototype.luma = function () {
        var t = this.r / p, i = this.g / p, n = this.b / p;
        return t = t <= .03928 ? t / 12.92 : e.pow((t + .055) / 1.055, 2.4), i = i <= .03928 ? i / 12.92 : e.pow((i + .055) / 1.055, 2.4), n = n <= .03928 ? n / 12.92 : e.pow((n + .055) / 1.055, 2.4), .2126 * t + .7152 * i + .0722 * n
    }, C.prototype.saturate = function (t) {
        var e = this.toHsl();
        return e.s += t / u, e.s = l(e.s), this.rgb(s(e))
    }, C.prototype.desaturate = function (t) {
        return this.saturate(-t)
    }, C.prototype.contrast = function (t, e, i) {
        if (e = v(e) ? new C(p, p, p, 1) : new C(e), t = v(t) ? new C(0, 0, 0, 1) : new C(t), t.luma() > e.luma()) {
            var n = e;
            e = t, t = n
        }
        return this.a < .5 ? t : (i = v(i) ? .43 : c(i), this.luma() < i ? e : t)
    }, C.prototype.hexStr = function () {
        var t = this.r.toString(16), e = this.g.toString(16), i = this.b.toString(16);
        return 1 == t.length && (t = "0" + t), 1 == e.length && (e = "0" + e), 1 == i.length && (i = "0" + i), "#" + t + e + i
    }, C.prototype.toCssStr = function () {
        var t = this;
        return t.a > 0 ? t.a < 1 ? "rgba(" + t.r + "," + t.g + "," + t.b + "," + t.a + ")" : t.hexStr() : "transparent"
    }, C.isColor = a, C.names = m, C.get = function (t) {
        return new C(t)
    }, t.zui({Color: C})
}(jQuery, Math, window, void 0), function (t) {
    "use strict";
    function e(e, i) {
        if (e === !1)return e;
        if (!e)return i;
        e === !0 ? e = {add: !0, "delete": !0, edit: !0, sort: !0} : "string" == typeof e && (e = e.split(","));
        var n;
        return t.isArray(e) && (n = {}, t.each(e, function (e, i) {
            t.isPlainObject(i) ? n[i.action] = i : n[i] = !0
        }), e = n), t.isPlainObject(e) && (n = {}, t.each(e, function (e, i) {
            i ? n[e] = t.extend({type: e}, s[e], t.isPlainObject(i) ? i : null) : n[e] = !1
        }), e = n), i ? t.extend(!0, {}, i, e) : e
    }

    function i(e, i, n) {
        return i = i || e.type, t(n || e.template).addClass("tree-action").attr(t.extend({
            "data-type": i,
            title: e.title || ""
        }, e.attr)).data("action", e)
    }

    var n = "zui.tree", o = 0, a = function (e, i) {
        this.name = n, this.$ = t(e), this.getOptions(i), this._init()
    }, s = {
        sort: {template: '<a class="sort-handler" href="javascript:;"><i class="icon icon-move"></i></a>'},
        add: {template: '<a href="javascript:;"><i class="icon icon-plus"></i></a>'},
        edit: {template: '<a href="javascript:;"><i class="icon icon-pencil"></i></a>'},
        "delete": {template: '<a href="javascript:;"><i class="icon icon-trash"></i></a>'}
    };
    a.DEFAULTS = {
        animate: null,
        initialState: "normal",
        toggleTemplate: '<i class="list-toggle icon"></i>'
    }, a.prototype.add = function (e, i, n, o, a) {
        var s, r = t(e), l = this.options;
        if (r.is("li") ? (s = r.children("ul"), s.length || (s = t("<ul/>"), r.append(s), this._initList(s, r))) : s = r, s) {
            var c = this;
            t.isArray(i) || (i = [i]), t.each(i, function (e, i) {
                var n = t("<li/>").data(i).appendTo(s);
                void 0 !== i.id && n.attr("data-id", i.id);
                var o = l.itemWrapper ? t(l.itemWrapper === !0 ? '<div class="tree-item-wrapper"/>' : l.itemWrapper).appendTo(n) : n;
                if (i.html) o.html(i.html); else if (t.isFunction(c.options.itemCreator)) {
                    var a = c.options.itemCreator(n, i);
                    a !== !0 && a !== !1 && o.html(a)
                } else i.url ? o.append(t("<a/>", {href: i.url}).text(i.title || i.name)) : o.append(t("<span/>").text(i.title || i.name));
                c._initItem(n, i.idx || e, s, i), i.children && i.children.length && c.add(n, i.children)
            }), this._initList(s), n && !s.hasClass("tree") && c.expand(s.parent("li"), o, a)
        }
    }, a.prototype.reload = function (e) {
        var i = this;
        e && (i.$.empty(), i.add(i.$, e)), i.isPreserve && i.store.time && i.$.find("li:not(.tree-action-item)").each(function () {
            var e = t(this);
            i[i.store[e.data("id")] ? "expand" : "collapse"](e, !0, !0)
        })
    }, a.prototype._initList = function (n, o, a, s) {
        var r = this;
        n.hasClass("tree") ? (a = 0, o = null) : (o = (o || n.closest("li")).addClass("has-list"), o.find(".list-toggle").length || o.prepend(this.options.toggleTemplate), a = a || o.data("idx"));
        var l = n.attr("data-idx", a || 0).children("li:not(.tree-action-item)").each(function (e) {
            r._initItem(t(this), e + 1, n)
        });
        1 !== l.length || l.find("ul").length || l.addClass("tree-single-item"), s = s || (o ? o.data() : null);
        var c = e(s ? s.actions : null, this.actions);
        if (c) {
            if (c.add && c.add.templateInList !== !1) {
                var d = n.children("li.tree-action-item");
                d.length ? d.detach().appendTo(n) : t('<li class="tree-action-item"/>').append(i(c.add, "add", c.add.templateInList)).appendTo(n)
            }
            c.sort && n.sortable(t.extend({
                dragCssClass: "tree-drag-holder",
                trigger: ".sort-handler",
                selector: "li:not(.tree-action-item)",
                finish: function (t) {
                    r.callEvent("action", {action: c.sort, $list: n, target: t.target, item: s})
                }
            }, c.sort.options, t.isPlainObject(this.options.sortable) ? this.options.sortable : null))
        }
        o && (o.hasClass("open") || s && s.open) && o.addClass("open in")
    }, a.prototype._initItem = function (n, o, a, s) {
        if (void 0 === o) {
            var r = n.prev("li");
            o = r.length ? r.data("idx") + 1 : 1
        }
        if (a = a || n.closest("ul"), n.attr("data-idx", o).removeClass("tree-single-item"), !n.data("id")) {
            var l = o;
            a.hasClass("tree") || (l = a.parent("li").data("id") + "-" + l), n.attr("data-id", l)
        }
        s = s || n.data();
        var c = e(s.actions, this.actions);
        if (c) {
            var d = n.find(".tree-actions");
            d.length || (d = t('<div class="tree-actions"/>').appendTo(this.options.itemWrapper ? n.find(".tree-item-wrapper") : n), t.each(c, function (t, e) {
                e && d.append(i(e, t))
            }))
        }
        var p = n.children("ul");
        p.length && this._initList(p, n, o, s)
    }, a.prototype._init = function () {
        var i = this.options, a = this;
        this.actions = e(i.actions), this.$.addClass("tree"), i.animate && this.$.addClass("tree-animate"), this._initList(this.$);
        var s = i.initialState, r = t.zui && t.zui.store && t.zui.store.enable;
        r && (this.selector = n + "::" + (i.name || "") + "#" + (this.$.attr("id") || o++), this.store = t.zui.store[i.name ? "get" : "pageGet"](this.selector, {})), "preserve" === s && (r ? this.isPreserve = !0 : this.options.initialState = s = "normal"), this.reload(i.data), r && (this.isPreserve = !0), "expand" === s ? this.expand() : "collapse" === s && this.collapse(), this.$.on("click", '.list-toggle,a[href="#"],.tree-toggle', function (e) {
            var i = t(this), n = i.parent("li");
            a.callEvent("hit", {target: n, item: n.data()}), a.toggle(n), i.is("a") && e.preventDefault()
        }).on("click", ".tree-action", function () {
            var e = t(this), i = e.data();
            if (i.action && (i = i.action), "sort" !== i.type) {
                var n = e.closest("li:not(.tree-action-item)");
                a.callEvent("action", {action: i, target: this, $item: n, item: n.data()})
            }
        })
    }, a.prototype.preserve = function (e, i, n) {
        if (this.isPreserve)if (e) i = i || e.data("id"), n = void 0 === n && e.hasClass("open"), n ? this.store[i] = n : delete this.store[i], this.store.time = (new Date).getTime(), t.zui.store[this.options.name ? "set" : "pageSet"](this.selector, this.store); else {
            var o = this;
            this.store = {}, this.$.find("li").each(function () {
                o.preserve(t(this))
            })
        }
    }, a.prototype.expand = function (t, e, i) {
        t ? (t.addClass("open"), !e && this.options.animate ? setTimeout(function () {
            t.addClass("in")
        }, 10) : t.addClass("in")) : t = this.$.find("li.has-list").addClass("open in"), i || this.preserve(t), this.callEvent("expand", t, this)
    }, a.prototype.show = function (e, i, n) {
        var o = this;
        e.each(function () {
            var e = t(this);
            if (o.expand(e, i, n), e)for (var a = e.parent("ul"); a && a.length && !a.hasClass("tree");) {
                var s = a.parent("li");
                s.length ? (o.expand(s, i, n), a = s.parent("ul")) : a = !1
            }
        })
    }, a.prototype.collapse = function (t, e, i) {
        t ? !e && this.options.animate ? (t.removeClass("in"), setTimeout(function () {
            t.removeClass("open")
        }, 300)) : t.removeClass("open in") : t = this.$.find("li.has-list").removeClass("open in"), i || this.preserve(t), this.callEvent("collapse", t, this)
    }, a.prototype.toggle = function (t) {
        var e = t && t.hasClass("open") || t === !1 || void 0 === t && this.$.find("li.has-list.open").length;
        this[e ? "collapse" : "expand"](t)
    }, a.prototype.getOptions = function (e) {
        this.options = t.extend({}, a.DEFAULTS, this.$.data(), e), null === this.options.animate && this.$.hasClass("tree-animate") && (this.options.animate = !0)
    }, a.prototype.toData = function (e, i) {
        t.isFunction(e) && (i = e, e = null), e = e || this.$;
        var n = this;
        return e.children("li:not(.tree-action-item)").map(function () {
            var e = t(this), o = e.data();
            delete o["zui.droppable"];
            var a = e.children("ul");
            return a.length && (o.children = n.toData(a)), t.isFunction(i) ? i(o, e) : o
        }).get()
    }, a.prototype.callEvent = function (e, i) {
        var n;
        return t.isFunction(this.options[e]) && (n = this.options[e](i, this)), this.$.trigger(t.Event(e + "." + this.name, i)), n
    }, t.fn.tree = function (e, i) {
        return this.each(function () {
            var o = t(this), s = o.data(n), r = "object" == typeof e && e;
            s || o.data(n, s = new a(this, r)), "string" == typeof e && s[e](i)
        })
    }, t.fn.tree.Constructor = a, t(function () {
        t('[data-ride="tree"]').tree()
    })
}(jQuery);