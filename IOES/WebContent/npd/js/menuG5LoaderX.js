// Menu G5.6.3 (non-frame/loader)
// Last Modified: Jun. 27, 2006
// Web Site: yxScripts.com
// Email: m_yangxin@hotmail.com

// Copyright 2003, 2004  Xin Yang   All Rights Reserved.

var _scriptPath="http://www.yxScripts.com/menuG5/script/";
var _menuTimer=500;
var _floatTimer=100;
var _floatOffset=1;
var _zBase=2;
var _menuMargin=1;
var _showMessage=1;
var _showToolTip=0;
var _onePixelGIF="onePixel.gif";
var _inheritStyle=1;
var _minimumWidth=0;
var _supportIEMac=1;
var _supportSafari=1;

Object.prototype.copy=function(){var o=new Object();for(var i in this){o[i]=typeof(this[i])=='object'?this[i].copy():this[i]}return o};Array.prototype.copy=function(){var a=new Array();for(var i in this){a[i]=typeof(this[i])=='object'?this[i].copy():this[i]}return a};Array.prototype.shift=Array.prototype.shift||function(){var a=this[0]||null;for(var i=0;i<this.length-1;i++){this[i]=this[i+1]};if(this.length>0){this.length--};return a};Array.prototype.push=Array.prototype.push||function(){for(var i=0;i<arguments.length;i++){this[this.length]=arguments[i]};return this[this.length-1]};if(typeof(webPath)=="undefined"){webPath=""};if(typeof(scriptPath)=="undefined"){scriptPath=_scriptPath};if(typeof(menuTimer)=="undefined"){menuTimer=_menuTimer};if(typeof(floatTimer)=="undefined"){floatTimer=_floatTimer};if(typeof(floatOffset)=="undefined"){floatOffset=_floatOffset};if(typeof(zBase)=="undefined"){zBase=_zBase};if(typeof(menuMargin)=="undefined"){menuMargin=_menuMargin};if(typeof(showMessage)=="undefined"){showMessage=_showMessage};if(typeof(showToolTip)=="undefined"){showToolTip=_showToolTip};if(typeof(onePixelGIF)=="undefined"){onePixelGIF=scriptPath+_onePixelGIF};if(typeof(inheritStyle)=="undefined"){inheritStyle=_inheritStyle};if(typeof(minimumWidth)=="undefined"){minimumWidth=_minimumWidth};if(typeof(supportIEMac)=="undefined"){supportIEMac=_supportIEMac};if(typeof(supportSafari)=="undefined"){supportSafari=_supportSafari};var yx_fd=navigator.userAgent.toLowerCase();var yx_isMac=(yx_fd.indexOf("mac")!=-1);var yx_isOpera=(yx_fd.indexOf("opera")!=-1);var yx_isOpera7=yx_fd.search(/opera[\s\/]+(\d+)/)!=-1?parseFloat(RegExp.$1)>=7:false;var yx_isSafari=(yx_fd.indexOf("safari")!=-1);var yx_isIE=(yx_fd.indexOf("msie")!=-1&&!yx_isOpera);var yx_isIE5=(yx_isIE&&yx_fd.indexOf("msie 4")==-1);var yx_isIE55=(yx_isIE&&yx_fd.indexOf("msie 5.5")!=-1);var yx_isIE6up=(yx_isIE&&yx_fd.search(/msie\s+(\d+)/)!=-1?parseFloat(RegExp.$1)>=6:false);var yx_isIE55up=(yx_isIE55||yx_isIE6up);var yx_isGecko=(yx_fd.indexOf("gecko")!=-1&&!yx_isSafari);var yx_isNetscape=(yx_isGecko&&yx_fd.indexOf("netscape")!=-1);var yx_menuSafe=(yx_isGecko||yx_isOpera7||yx_isSafari&&supportSafari||yx_isIE5&&yx_isMac&&supportIEMac||yx_isIE5&&!yx_isMac);var yx_M="M",yx_L="L",yx_C="C",yx_I="I",yx_S="S";var yx_dr=true,yx_ic="",yx_el=false,yx_fc=new Array(),yx_ee=false;var yx_al=0,yx_an=new Array();var yx_fm=0,yx_fj=0,yx_gg=0,yx_ca=menuMargin,yx_gf=0,yx_gh=0,yx_ih=0,yx_id=0;var yx_fx=new Image(1,1);yx_fx.src=onePixelGIF;var yx_fw=/(^\w+:)|(^\/)|(^\.)/,yx_bi=/css\s*:([\w\-\s]+),?([\w\-\s]+)?,?([\w\-\s]+)?,?([\w\-\s]+)?,?([\w\-\s]+)?/,yx_bj=/css2\s*:([\w\-\s]+),?([\w\-\s]+)?,?([\w\-\s]+)?,?([\w\-\s]+)?,?([\w\-\s]+)?/,yx_fb=/\w/;var yx_fv=1,yx_hs=2,yx_ej=3,yx_be=4,yx_eh=5,yx_bd=6;function yx_ff(fu){return document.createElement(fu)};function yx_fg(ae,w,h,aa){var l=yx_ff("IMG");with(l){src=ae;width=w;height=h;border=0;hspace=0;vspace=0;align=aa};return l};function yx_fe(x,y,gh,cu){var l=yx_ff("DIV");with(l.style){position="absolute";visibility=gh;left=x+"px";top=y+"px";zIndex=cu};return l};function yx_ci(n){return document.getElementById(n)};function yx_gn(l,cu){l.style.zIndex=cu};function yx_dp(l){return l.style.zIndex};function yx_gq(l){l.style.visibility="visible"};function yx_dv(l){l.style.visibility="hidden"};function yx_ey(l,x,y){l.style.left=x+"px";l.style.top=y+"px"};function yx_ex(l,x,y){l.style.left=(parseInt(l.style.left)+x)+"px";l.style.top=(parseInt(l.style.top)+y)+"px"};function yx_at(menu){for(var i=0;i<menu.df.length;i++){if(menu.df[i].menu&&menu.df[i].menu.ew){yx_at(menu.df[i].menu)}};try{document.body.removeChild(menu.holder);if(menu.cv){document.body.removeChild(menu.cv)}}catch(err){}};function yx_bw(){var item=this.item,si=item.fm;yx_gv();yx_gw(yx_gg);yx_gg=0;if(si.fb.ef||item.au.gg!=yx_M){yx_gj(item,si.fb.ba,si.fe.ba,si.fa.ba,si.fa.bb,si.ez.ba,true,si.fb.gj,si.fb.gq,si.fb.by)}};function yx_av(){yx_ee=true};function yx_ax(e){var item=this.item,au=item.au,gg=au.gg,menu=item.dg;yx_gv();if(gg==yx_M||gg==yx_L){if(!item.fm.fb.ef&&gg==yx_M){if(item.menu.fl){item.menu.co(false);menu.open=false;this.dm(0)}else if(!item.menu.go){menu.open=true;this.dm(1);yx_gw(yx_fm);yx_fm=yx_ak(item,"ed","()",0)}}else if(au.di!=""){yx_az(null);yx_dq(au.di,menu.cl.target,yx_go(e))}}else if(au.ar!=""){yx_az(null);eval(au.ar)}};function yx_cb(bu){return(bu==null?0:bu.offsetWidth>0?bu.offsetWidth:bu.hasChildNodes()&&bu.firstChild.nodeType==1?bu.firstChild.offsetWidth:0)};function yx_ei(item){return yx_cb(item.bl)+yx_cb(item.fu)+yx_cb(item.cs)+yx_cb(item.ct)};function yx_gm(){yx_fj=setTimeout("yx_az(null)",menuTimer*2)};function yx_gw(t){if(t>0)clearTimeout(t)};function yx_gu(){yx_ee=false;yx_gw(yx_fm);yx_fm=0;yx_gw(yx_fj);yx_fj=0;yx_gw(yx_gg);yx_gg=0};function yx_dc(es){return yx_fw.test(es)?es:(webPath+es)};function yx_gs(du){if(showMessage==1)window.status=du};function yx_ak(eb,cf,er,t){var x=yx_al++;yx_an[x]=null;yx_an[x]=eb;var tm=setTimeout(("yx_an["+x+"]."+cf+er),t);if(yx_al>200){yx_al=0};return tm};function yx_dq(di,ft,nw){if(di.substring(0,11).toLowerCase()=="javascript:"){eval(di.substring(11))}else{if(nw){window.open(yx_dc(di))}else if(ft!=""){window.open(yx_dc(di),ft)}else{window.location.href=yx_dc(di)}}};function yx_ck(eb,n){for(var i=0;i<eb.length;i++){if(eb[i].name==n){return eb[i]}};return null};function yx_da(eb,n){for(var i=0;i<eb.length;i++){if(eb[i].name==n){return eb[i]}};return eb[0]};function yx_hw(s){return(s.replace(/^\s+/,"")).replace(/\s$/,"")};function yx_cl(er,re){var ra=er.match(re),pa=new Array();pa[0]=(ra&&ra[1]&&yx_fb.test(ra[1]))?yx_hw(ra[1]):"";pa[1]=(ra&&ra[2]&&yx_fb.test(ra[2]))?yx_hw(ra[2]):pa[0];pa[2]=(ra&&ra[3]&&yx_fb.test(ra[3]))?yx_hw(ra[3]):pa[1];pa[3]=(ra&&ra[4]&&yx_fb.test(ra[4]))?yx_hw(ra[4]):pa[1];pa[4]=(ra&&ra[5]&&yx_fb.test(ra[5]))?yx_hw(ra[5]):pa[0];return pa};function yx_hh(sp,er){this.name=sp;this.bc=er.search(/pad-css\s*:([\w\-\s]+)/)!=-1?yx_hw(RegExp.$1):"";this.ck=er.search(/holder-css\s*:([\w\-\s]+)/)!=-1?yx_hw(RegExp.$1):"";this.bar=(er.search(/menu-form\s*:\s*bar/)!=-1);this.bk=er.search(/direction\s*:\s*(right-down|right-up|left-down|left-up|center-down|center-up|abs-right-down|abs-right-up|abs-left-down|abs-left-up|abs-center-up|abs-center-down|right-top|left-top|right-middle|left-middle|right-bottom|left-bottom)/)!=-1?(RegExp.$1):"";this.scroll=er.search(/scroll\s*:\s*(both|x-only|y-only|none)/)!=-1?(RegExp.$1):"none";this.step=er.search(/step\s*:\s*(\d+)/)!=-1?parseInt(RegExp.$1,10):0;this.flip=(er.search(/flip\s*:\s*no/)==-1);this.dx=er.search(/offset-left\s*:\s*(-?\d+)/)!=-1?parseInt(RegExp.$1,10):0;this.dy=er.search(/offset-top\s*:\s*(-?\d+)/)!=-1?parseInt(RegExp.$1,10):0;var ra=er.match(/item-offset\s*:\s*(-?\d+)\s*,?\s*(-?\d+)?/);this.cq=(ra&&ra[1])?parseInt(ra[1],10):0;this.cr=(ra&&ra[2])?parseInt(ra[2],10):this.cq;this.col=er.search(/col\s*:\s*(\d+)/)!=-1?parseInt(RegExp.$1,10):0;this.row=er.search(/row\s*:\s*(\d+)/)!=-1?parseInt(RegExp.$1,10):0;ra=er.match(/filters\s*:\s*(\w+)\s*,?\s*(\w+)?/);this.ca=!(ra&&ra[1]=="no");this.bx=(ra&&ra[2]=="yes");ra=er.match(/tiles\s*:\s*(\d+)\s*,?\s*(\d+)\s*:([\w\-\s]+),([\w\-\s]+),([\w\-\s]+),([\w\-\s]+),([\w\-\s]+),([\w\-\s]+),([\w\-\s]+),([\w\-\s]+),([\w\-\s]+)/);this.fz=(ra&&ra[1])?parseInt(ra[1],10):0;this.fy=(ra&&ra[2])?parseInt(ra[2],10):0;this.tiles=new Array();if(this.fz>0||this.fy>0){for(var i=0;i<9;i++){this.tiles[i]=(ra&&ra[i+3]&&yx_fb.test(ra[i+3]))?yx_hw(ra[i+3]):""}}};var yx_hi=new Array(new yx_hh("",""));function yx_hd(si,er){this.name=si;var pa=yx_cl(er,yx_bi);this.bc=pa[0];this.ay=pa[1];this.ba=pa[2];this.bg=pa[3];this.be=[pa[0],pa[4]];this.cursor=er.search(/cursor\s*:([\w\-\s]+)/)!=-1?yx_hw(RegExp.$1):"";this.gj=er.search(/align\s*:\s*(left|center|right)/)!=-1?(RegExp.$1):"center";this.gq=er.search(/valign\s*:\s*(top|middle|bottom)/)!=-1?(RegExp.$1):"middle";this.actual=(er.search(/width\s*:\s*actual/)!=-1);this.ef=(er.search(/sub-menu\s*:\s*mouse-click/)==-1);var ra=er.match(/filters\s*:\s*(\w+)\s*,?\s*(\w+)?\s*,?\s*(\w+)?/);this.bz=(ra&&ra[1]=="yes");this.bw=!(ra&&ra[2]=="no");this.by=(ra&&ra[3]=="yes")};var yx_he=new Array(new yx_hd("","width:actual;"));function yx_gx(sf,er){this.name=sf;var pa=yx_cl(er,yx_bi);this.bc=pa[0];this.ay=pa[1];this.ba=pa[2];this.bg=pa[3];this.be=[pa[0],pa[4]]};var yx_gy=new Array(new yx_gx("",""));function yx_hl(st,er){this.name=st;var pa=yx_cl(er,yx_bi);this.bc=pa[0];this.ay=pa[1];this.ba=pa[2];this.bg=pa[3];this.be=[pa[0],pa[4]];this.text=er.search(/text\s*:([^:]+):/)!=-1?yx_hw(RegExp.$1):""};var yx_hm=new Array(new yx_hl("",""));function yx_hb(so,er){this.name=so;var pa=yx_cl(er,yx_bi);this.bc=pa[0];this.ay=pa[1];this.ba=pa[2];this.bg=pa[3];this.be=[pa[0],pa[4]];var ek=yx_cl(er,yx_bj);this.bd=ek[0];this.az=ek[1];this.bb=ek[2];this.bh=ek[3];this.bf=[ek[0],ek[4]];this.text=er.search(/text\s*:([^:]+):/)!=-1?yx_hw(RegExp.$1):"";this.text2=er.search(/text2\s*:([^:]+):/)!=-1?yx_hw(RegExp.$1):""};var yx_hc=new Array(new yx_hb("",""));function yx_hj(ss,er){this.name=ss;var ra=er.match(/css\s*:([\w\-\s]+),?([\w\-\s]+)?/);this.bi=(ra&&ra[1])?yx_hw(ra[1]):"";this.ax=(ra&&ra[2])?yx_hw(ra[2]):""};var yx_hk=new Array(new yx_hj("",""));function yx_hf(sm,sp,si,sf,st,so,ss){this.name=sm;this.fc=yx_db(sp);this.fb=yx_cv(si);this.ez=yx_cr(sf);this.fe=yx_di(st);this.fa=yx_cu(so);this.fd=yx_dg(ss)};var yx_hg=new Array(new yx_hf("","","","","","",""));function addStylePad(sp,er){yx_hi.push(new yx_hh(sp,er))};function addStyleItem(si,er){yx_he.push(new yx_hd(si,er))};function addStyleFont(sf,er){yx_gy.push(new yx_gx(sf,er))};function addStyleTag(st,er){yx_hm.push(new yx_hl(st,er))};function addStyleIcon(so,er){yx_hc.push(new yx_hb(so,er))};function addStyleSeparator(ss,er){yx_hk.push(new yx_hj(ss,er))};function addStyleMenu(sm,sp,si,sf,st,so,ss){yx_hg.push(new yx_hf(sm,sp,si,sf,st,so,ss))};function yx_ds(menu,style){this.menu=menu;this.style=style};function yx_gz(sg){this.name=sg;this.dn=new Array()};var yx_ha=new Array();function addStyleGroup(sg,sm){var gl=yx_ce(sg);if(gl==null){var x=yx_ha.length;yx_ha[x]=new yx_gz(sg);gl=yx_ha[x]};var gm=gl.dn.length;for(var i=2;i<addStyleGroup.arguments.length;i++){gl.dn[gm+i-2]=new yx_ds(addStyleGroup.arguments[i],sm)}};function setDefaultStyle(sp,si,sf,st,so,ss){if(sp!=""){yx_hg[0].fc=yx_db(sp)};if(si!=""){yx_hg[0].fb=yx_cv(si)};if(sf!=""){yx_hg[0].ez=yx_cr(sf)};if(st!=""){yx_hg[0].fe=yx_di(st)};if(so!=""){yx_hg[0].fa=yx_cu(so)};if(ss!=""){yx_hg[0].fd=yx_dg(ss)}};function yx_db(sp){return yx_da(yx_hi,sp)};function yx_cv(si){return yx_da(yx_he,si)};function yx_cr(sf){return yx_da(yx_gy,sf)};function yx_di(st){return yx_da(yx_hm,st)};function yx_cu(so){return yx_da(yx_hc,so)};function yx_dg(ss){return yx_da(yx_hk,ss)};function yx_cy(sm){return yx_ck(yx_hg,sm)};function yx_ce(sg){return yx_ck(yx_ha,sg)};function yx_cd(n){return yx_ck(yx_bb,n)};function yx_ch(n){return yx_ck(yx_dx,n)};function yx_cj(n){return yx_ck(yx_em,n)};function yx_ek(bl,du,di,id){this.gg=yx_L;this.bl=bl;this.du=du;this.di=di;this.ci=id;this.eg=0};function yx_ba(bl,du,ar,id){this.gg=yx_C;this.bl=bl;this.du=du;this.ar=ar;this.ci=id;this.eg=0};function yx_dz(bl,id){this.gg=yx_I;this.bl=bl;this.ci=id};function yx_gi(id){this.gg=yx_S;this.ci=id};function yx_ho(bl,du,di,sub,id){this.name=sub;this.gg=yx_M;this.bl=bl;this.du=du;this.di=di;this.ci=id;this.eg=0;this.df=new Array();this.am=yx_ad;this.al=yx_ac;this.aj=yx_aa;this.ak=yx_ab;this.an=yx_ae};function yx_hv(tm){this.name=tm;this.df=new Array();this.am=yx_ad;this.al=yx_ac;this.aj=yx_aa;this.ak=yx_ab;this.an=yx_ae};function yx_bc(n,eb){this.name=n;this.menu=eb};yx_bb=new Array();function yx_ad(bl,du,di,sub,id){if(typeof(yx_fc[sub])=="undefined"){yx_fc[sub]=new yx_ho(bl,du,di,sub,id)};this.df.push(yx_fc[sub])};function yx_ac(bl,du,di,id){this.df.push(new yx_ek(bl,du,di,id))};function yx_aa(bl,du,ar,id){this.df.push(new yx_ba(bl,du,ar,id))};function yx_ab(bl,id){this.df.push(new yx_dz(bl,id))};function yx_ae(id){this.df.push(new yx_gi(id))};function yx_af(n){yx_dr=false;yx_gs("["+n+"] Not Found.")};function addMenu(n,tm){if(yx_dr){yx_fc[tm]=new yx_hv(tm);var c=yx_cd(n);if(c==null){yx_bb.push(new yx_bc(n,yx_fc[tm]))}else{c.menu=yx_fc[tm]}}};function addSubMenu(n,bl,du,di,sub,id){if(yx_dr){if(typeof(yx_fc[n])!="undefined"){yx_fc[n].am(bl,du||"",di||"",sub,id||"")}else{yx_af(n)}}};function addLink(n,bl,du,di,id){if(yx_dr){if(typeof(yx_fc[n])!="undefined"){yx_fc[n].al(bl,du||"",di||"",id||"")}else{yx_af(n)}}};function addCommand(n,bl,du,ar,id){if(yx_dr){if(typeof(yx_fc[n])!="undefined"){yx_fc[n].aj(bl,du||"",ar||"",id||"")}else{yx_af(n)}}};function addInfo(n,bl,id){if(yx_dr){if(typeof(yx_fc[n])!="undefined"){yx_fc[n].ak(bl,id||"")}else{yx_af(n)}}};function addSeparator(n,id){if(yx_dr){if(typeof(yx_fc[n])!="undefined"){yx_fc[n].an(id||"")}else{yx_af(n)}}};function endMenu(){if(yx_dr){yx_gs("Menu Parsed")}};function yx_cz(menu){if(menu.cl.style!=null){var fo=menu.cl.style.dn;for(var i=0;i<fo.length;i++){if(fo[i].menu==menu.au.name){return yx_cy(fo[i].style)}}};return(menu.dg==menu.cl||inheritStyle==0)?yx_hg[0]:menu.dg.dg.style};function yx_cw(item){if(item.au.ci!=""&&item.dg.cl.style!=null){var fo=item.dg.cl.style.dn;for(var i=0;i<fo.length;i++){if(fo[i].menu==item.au.ci){return yx_cy(fo[i].style)}}};return item.dg.style};function yx_ii(x,y){this.x=x;this.y=y};function yx_dh(n){yx_cp();var x=(n==0||n==6||n==7)?0:((n==1||n==5||n== 8)?Math.round(yx_ih/2):(yx_ih-1));var y=(n==0||n==1||n==2)?0:((n==3||n==7||n== 8)?Math.round(yx_id/2):(yx_id-1));return(new yx_ii(x,y))};function yx_cf(n,dp){var cy=yx_ck(yx_ea,n);return dp?((cy!=null&&cy.menu!=null&&cy.menu.ew)?cy:null):cy};function yx_az(cy){for(var i=0;i<yx_ea.length;i++){if(yx_ea[i].menu.fl&&yx_ea[i]!=cy){yx_ea[i].cm()}}};function yx_gl(cy,cu){cy.z=cu;yx_gn(cy.menu.holder,cu)};function addInstance(n,mc,er){if(yx_menuSafe){var au=yx_cd(mc);if(au!=null){for(var i=0;i<yx_ea.length;i++){if(yx_ea[i].name==n){yx_bx(i);break}};yx_ea.push(new yx_ec(n,au.copy().menu,er));yx_gs("Menu ["+n+"] Enabled")}}};function yx_cc(n,bs,bt){this.name=n;this.as=bs;this.at=bt};var yx_dx=new Array(),yx_em=new Array();function addItemEvent(n,bs,bt){var x=yx_ch(n);if(x==null){yx_dx.push(new yx_cc(n,bs,bt))}};function addMenuEvent(n,bs,bt){var x=yx_cj(n);if(x==null){yx_em.push(new yx_cc(n,bs,bt))}};function addWindowEvent(eh){yx_ic=eh};function yx_bl(x,y,w,h,sx,sy){this.x=x;this.y=y;this.width=w;this.height=h;this.scrollx=sx;this.scrolly=sy};function yx_cp(){yx_gf=yx_de();yx_gh=yx_df();yx_ih=yx_do();yx_id=yx_dn()};function yx_fy(s){for(var i=1;i<yx_fy.arguments.length&&s.indexOf('%')!=-1;i++){s=s.substring(0,s.indexOf('%'))+yx_fy.arguments[i]+s.substring(s.indexOf('%')+1)};return s};function yx_dk(fc,w,h){var fx="",fv="<td class='%' width='%' height='%'></td>",tw=fc.fz,th=fc.fy;if(th>0){if(tw>0){fx+=yx_fy("<tr>"+fv+fv+fv+"</tr>",fc.tiles[0],tw,th,fc.tiles[1],w-tw*2,th,fc.tiles[2],tw,th)}else{fx+=yx_fy("<tr>"+fv+"</tr>",fc.tiles[1],w,th)}};if(tw>0){fx+=yx_fy("<tr>"+fv+fv+fv+"</tr>",fc.tiles[3],tw,h-th*2,fc.tiles[4],w-tw*2,h-th*2,fc.tiles[5],tw,h-th*2)}else{fx+=yx_fy("<tr>"+fv+"</tr>",fc.tiles[4],w,h-th*2)};if(th>0){if(tw>0){fx+=yx_fy("<tr>"+fv+fv+fv+"</tr>",fc.tiles[6],tw,th,fc.tiles[7],w-tw*2,th,fc.tiles[8],tw,th)}else{fx+=yx_fy("<tr>"+fv+"</tr>",fc.tiles[7],w,th)}};return "<table cellpadding='0' cellspacing='0' border='0'>"+fx+"</table>"};function yx_dd(dp){var cl=this.cl,ei=this.dg,ej=ei.dg,fc=this.style.fc,sW=this.sW,sH=this.sH;var gn=cl.gj,gs=cl.gq,bk=fc.bk==""?(cl.bk):(fc.bk);yx_cp();var ep=0,eq=0;if(ei==cl){ep=gn=="left"?0:gn=="center"?(-Math.round(this.width/2)):(-this.width+1);eq=gs=="top"?0:gs=="middle"?(-Math.round(this.height/2)):(-this.height+1);if(cl.floating&&cl.gc>0){yx_gw(cl.gc);cl.gc=-1;cl.holder.x=cl.ox;cl.holder.y=cl.oy};ep+=cl.holder.x;eq+=cl.holder.y;if(cl.et=="slot"||cl.floating){ep+=yx_gf;eq+=yx_gh}}else{var iX=ej.x+ei.x,iY=ej.y+ei.y,iW=ei.width,iH=ei.height;if(ej.bar){eq=bk.indexOf("up")!=-1?(iY-this.height+sH):(iY+iH-sH);ep=bk.indexOf("abs-center")!=-1?(ej.x+Math.round((ej.width-this.width)/2)):bk.indexOf("abs-left")!=-1?ej.x:bk.indexOf("abs-right")!=-1?(ej.x+ej.width-this.width):bk.indexOf("center")!=-1?(iX+Math.round((iW-this.width)/2)):bk.indexOf("left")!=-1?(iX+iW-this.width+sW):(iX-sW)}else{ep=bk.indexOf("left")!=-1?(iX-this.width+sW):(iX+iW-sW);eq=bk.indexOf("top")!=-1?ej.y:bk.indexOf("bottom")!=-1?(ej.y+ej.height-this.height):bk.indexOf("middle")!=-1?(ej.y+Math.round((ej.height-this.height)/2)):bk.indexOf("up")!=-1?(iY+iH-this.height+sH):(iY-sH)};ep+=fc.dx;eq+=fc.dy;if(fc.flip){var eo=eq,el=eo+this.height-1,em=ep,en=em+this.width-1;var dx=0,bq=0,dy=0,br=0;if(ej.bar){if(bk.indexOf("down")!=-1&&el-yx_gh>=yx_id&&eo-this.height-ei.height+2*sH>=yx_gh){dy=-this.height-ei.height+2*sH-2*fc.dy;eo+=dy;el+=dy};if(bk.indexOf("up")!=-1&&eo<yx_gh&&el+this.height+ei.height-2*sH-yx_gh<yx_id){dy=this.height+ei.height-2*sH-2*fc.dy;eo+=dy;el+=dy}}else{if(bk.indexOf("right")!=-1&&en-yx_gf>=yx_ih&&em-this.width-ei.width+2*sW>=yx_gf){dx=-this.width-ei.width+2*sW-2*fc.dx;em+=dx;en+=dx};if(bk.indexOf("left")!=-1&&em<yx_gf&&en+this.width+ei.width-2*sW-yx_gf<yx_ih){dx=this.width+ei.width-2*sW-2*fc.dx;em+=dx;en+=dx}};if(el-yx_gh>=yx_id){br=yx_id+yx_gh-el-yx_ca};if(eo+br<yx_gh){br=yx_gh-eo+yx_ca};if(en-yx_gf>=yx_ih){bq=yx_ih+yx_gf-en-yx_ca};if(em+bq<yx_gf){bq=yx_gf-em+yx_ca};ep+=dx+bq;eq+=dy+br}};this.dt(ep,eq);if(dp&&!this.fl&&(ej==null||ej.ai==ei)){yx_gn(this.holder,ej!=null?(yx_dp(ej.holder)+1):(++zBase));yx_gq(this.holder);this.fl=true;for(var i=0;i<this.dd;i++){var gg=this.df[i].au.gg;if(gg==yx_M||gg==yx_L||gg==yx_C){this.df[i].ec()}};if(this.as!=""){eval(this.as)}};if(ei==cl&&cl.floating&&cl.gc<0){cl.sX=yx_gf;cl.sY=yx_gh;cl.ga()}};function yx_ag(){this.open=false;if(this.ai!=null){this.ai.ec();this.ai=null;for(var i=0;i<this.dd;i++){if(this.df[i].menu!=null){this.df[i].menu.co(true)}};if(this.dg==this.cl&&this.cl.gh&&this.at!=""){eval(this.at)}}};function yx_cn(mx,ix,bl,mw,iw){return(mx+ix<yx_gf||mx+ix-bl-iw<yx_gf&&mx<yx_gf)?iw:(mx+ix+iw-yx_gf>yx_ih||mx+ix+iw+bl+iw-yx_gf>yx_ih&&mx+mw-yx_gf>yx_ih)?-iw:0};function yx_co(my,iy,bl,mh,ih){return(my+iy<yx_gh||my+iy-bl-ih<yx_gh&&my<yx_gh)?ih:(my+iy+ih-yx_gh>yx_id||my+iy+ih+bl+ih-yx_gh>yx_id&&my+mh-yx_gh>yx_id)?-ih:0};function yx_ge(dx,dy){var bo=true;if(dx>0&&this.x+dx>=yx_gf){dx=yx_gf-this.x+yx_ca}else if(dx<0&&this.x+this.width-yx_gf+dx<=yx_ih){dx=yx_ih+yx_gf-this.x-this.width-yx_ca}else if(dy>0&&this.y+dy>=yx_gh){dy=yx_gh-this.y+yx_ca}else if(dy<0&&this.y+this.height-yx_gh+dy<=yx_id){dy=yx_id+yx_gh-this.y-this.height-yx_ca}else{bo=false};this.ds(dx,dy);if(!bo){yx_gg=yx_ak(this,"ff","("+dx+","+dy+")",floatTimer)}};function yx_eg(dg,au){this.dg=dg;this.au=au;this.fm=null;this.item=null;this.fu=null;this.cs=null;this.ct=null;this.bl=null;this.av=null;this.menu=null;this.x=0;this.y=0;this.ox=0;this.oy=0;this.width=0;this.height=0;this.ow=0;this.oh=0;this.as="";this.at="";this.dk=yx_eo;this.dj=yx_en;this.ed=yx_fk;this.ec=yx_fi};function yx_er(cl,dg,au){this.cl=cl;this.dg=dg;this.au=au;this.style=null;this.holder=null;this.pad=null;this.df=new Array();this.ai=null;this.dd=au.df.length;this.actual=false;this.ew=false;this.cb=false;this.go=false;this.fl=false;this.open=false;this.x=0;this.y=0;this.width=0;this.height=0;this.sW=0;this.sH=0;this.ch=yx_dd;this.as="";this.at="";this.dl=yx_ep;this.fj=yx_gr;this.co=yx_dw;this.ao=yx_ag;this.dt=yx_fa;this.ds=yx_ez;this.ff=yx_ge;this.bar=false};function yx_hq(menu,dp){for(var i=0;i<menu.dd;i++){var item=menu.df[i];if(item.au.eg==1){var si=item.fm;yx_gj(item,si.fb.be[dp],si.fe.be[dp],si.fa.be[dp],si.fa.bf[dp],si.ez.be[dp],true,si.fb.gj,si.fb.gq,false);if(item.au.gg==yx_M&&item.menu.ew){yx_hq(item.menu,dp)};break}}};function yx_hy(menu,es,dp){var bj=es.shift();for(var i=0;i<menu.df.length;i++){if(menu.df[i].ci==bj){menu.df[i].eg=dp;if(menu.df[i].gg==yx_M&&es.length>0){yx_hy(menu.df[i],es,dp)};break}}};function yx_gt(x){if(this.de.length>0){if(this.menu.ew){yx_hq(this.menu,0)};yx_hy(this.menu.au,this.de,0)};var dv=new Array();for(var i=1;i<x.length;i++){dv[i-1]=x[i]};if(dv.length>0){this.de=dv.copy();yx_hy(this.menu.au,dv,1);if(this.menu.ew){yx_hq(this.menu,1)}}};function yx_gb(){if(this.menu.ew){yx_hq(this.menu,0)};if(this.de.length>0){yx_hy(this.menu.au,this.de,0);this.de=new Array()}};function yx_ec(n,au,er){this.name=n;this.au=au;this.floating=(er.search(/floating\s*:\s*yes/)!=-1);var ra=er.match(/position\s*:\s*(absolute|relative|slot)\s*([\w\-]+)?/);this.et=(ra&&ra[1])?ra[1]:"absolute";this.ex=(ra&&ra[2])?ra[2]:"";this.gj=er.search(/align\s*:\s*(left|center|right)/)!=-1?(RegExp.$1):"left";this.gq=er.search(/valign\s*:\s*(top|middle|bottom)/)!=-1?(RegExp.$1):"top";this.dx=er.search(/offset-left\s*:\s*(-?\d+)/)!=-1?parseInt(RegExp.$1,10):0;this.dy=er.search(/offset-top\s*:\s*(-?\d+)/)!=-1?parseInt(RegExp.$1,10):0;this.bar=(er.search(/menu-form\s*:\s*bar/)!=-1);this.bk=er.search(/direction\s*:\s*(right-down|right-up|left-down|left-up|center-down|center-up|abs-right-down|abs-right-up|abs-left-down|abs-left-up|abs-center-up|abs-center-down|right-top|left-top|right-middle|left-middle|right-bottom|left-bottom)/)!=-1?(RegExp.$1):"right-down";this.gh=(er.search(/visibility\s*:\s*hidden/)==-1);this.target=er.search(/target\s*:\s*([\w\-]+)/)!=-1?(RegExp.$1):"";this.sticky=(er.search(/sticky\s*:\s*yes/)!=-1);this.highlight=(er.search(/highlight\s*:\s*no/)==-1);this.style=yx_ce(er.search(/style\s*:\s*([\w\-]+)/)!=-1?(RegExp.$1):"");this.dg=null;this.cl=this;this.menu=new yx_er(this,this,this.au);this.gc=-1;this.ga=yx_ht;this.sX=0;this.sY=0;this.z=zBase-1;this.holder=null;this.cg=yx_ct;this.ox=0;this.oy=0;this.de=new Array();this.fk=yx_gt;this.ey=yx_gb;this.fi=yx_gp;this.cm=yx_du;this.dr=yx_ew;this.dq=yx_ev};yx_ea=new Array();function yx_gp(){yx_ar();if(yx_el){if(this.holder==null){this.cg()};yx_az(this);this.menu.fj()}};function yx_du(){yx_ar();if(yx_el){this.menu.co(true)}};function yx_ew(x,y){if(this.menu.ew){this.holder.x=x;this.holder.y=y;this.menu.ch(false);this.menu.ao()}};function yx_ev(x,y){if(this.menu.ew){this.holder.x+=x;this.holder.y+=y;this.menu.ds(x,y);this.menu.ao()}};function yx_ht(){yx_cp();if(this.sX!=yx_gf||this.sY!=yx_gh){var mx=yx_gf>this.sX?Math.ceil:Math.floor,my=yx_gh>this.sY?Math.ceil:Math.floor;var dx=mx((yx_gf-this.sX)/floatOffset),dy=my((yx_gh-this.sY)/floatOffset);this.sX+=dx;this.sY+=dy;this.dq(dx,dy)};this.gc=yx_ak(this,"ga","()",floatTimer)};function yx_ai(item){yx_gn(item.item,yx_eh);yx_gn(item.av,yx_bd)};function yx_ah(item){yx_gn(item.item,yx_ej);yx_gn(item.av,yx_be)};function yx_fu(){yx_gu()};function yx_fr(){yx_gu();if(!this.sticky){yx_gm()}};function yx_ay(){yx_ee=false;yx_gv()};function yx_eq(dp){var item=this.item,menu=item.dg,eu=menu.ai;var si=item.fm,fb=si.fb,ez=si.ez,fe=si.fe,fa=si.fa;if(eu!=null&&eu!=item){eu.ec()};menu.ai=item;yx_ai(item);yx_gj(item,dp==0?fb.ay:fb.bg,dp==0?fe.ay:fe.bg,dp==0?fa.ay:fa.bg,dp==0?fa.az:fa.bh,dp==0?ez.ay:ez.bg,(dp==0&&item!=eu),fb.gj,fb.gq,dp==0?fb.bw:false)};function yx_hx(it){var x=it.dg.dg;if(x.dg!=null){x.av.dm(1);yx_hx(x)}};function yx_bv(it){var x=it.menu;if(x!=null&&x.fl){for(var i=0;i<x.dd;i++){if(x.df[i].menu!=null&&x.df[i].menu.fl){x.df[i].av.dm(1);yx_bv(x.df[i]);break}}}};function yx_fk(){var gg=this.au.gg,menu=this.dg,cl=menu.cl;yx_az(cl);if(cl.z<zBase){yx_gl(cl,++zBase)};for(var i=0;i<menu.dd;i++){if(menu.df[i]!=this&&menu.df[i].menu!=null){menu.df[i].menu.co(true)}};var fc=menu.style.fc;var dx=(fc.scroll=="none"||fc.scroll=="y-only")?0:yx_cn(menu.x,this.x,menu.bar?fc.cq:0,menu.width,fc.step==0?this.width:fc.step),dy=(fc.scroll=="none"||fc.scroll=="x-only")?0:yx_co(menu.y,this.y,menu.bar?0:fc.cr,menu.height,fc.step==0?this.height:fc.step);if(dx!=0||dy!=0){yx_gg=yx_ak(menu,"ff","("+dx+","+dy+")",menuTimer)}else if(gg==yx_M){if((this.fm.fb.ef||menu.open)&&!this.menu.fl&&this.menu.dd>0){this.menu.fj()}else if(!cl.highlight){this.menu.ao()}}};function yx_fi(){var si=this.fm,dp=this.au.eg;yx_ah(this);yx_gj(this,si.fb.be[dp],si.fe.be[dp],si.fa.be[dp],si.fa.bf[dp],si.ez.be[dp],true,si.fb.gj,si.fb.gq,si.fb.bz)};function yx_ft(){var item=this.item;if(item.dg.fl){yx_gu();yx_gs(item.au.du);if(item.as!=""){eval(item.as)};this.dm(item.au.gg==yx_M&&item.dg.open?1:0);yx_hx(item);yx_bv(item);yx_fm=yx_ak(item,"ed","()",menuTimer)}};function yx_fq(){yx_gu();if(this.item.at!=""){eval(this.item.at)};if(!this.sticky){yx_gm()}};function yx_ct(){var x=0,y=0;if(this.et=="relative"){var l=yx_ci(this.ex);if(l!=null){x=yx_cx(l);y=yx_dm(l)}}else if(this.et=="slot"){var s=yx_dh(parseInt(this.ex));x=s.x;y=s.y};x+=this.dx;y+=this.dy;this.ox=x;this.oy=y;this.holder=new yx_ii(x,y)};function yx_gd(){yx_gu();yx_fo();yx_cp();for(var i=0;i<yx_ea.length;i++){var cy=yx_ea[i];if(cy.menu.ew){yx_gw(cy.gc);cy.gc=-1;cy.cm();cy.cg();cy.menu.ch(false)}};if(yx_ic!=""){eval(yx_ic)}};function yx_aq(menu,id){var df=menu.df,item=null;for(var i=0;i<menu.dd;i++){if(df[i].au.ci==id){return df[i]}};for(var i=0;i<menu.dd;i++){if(df[i].menu!=null&&df[i].menu.ew){item=yx_aq(df[i].menu,id);if(item!=null){break}}};return item};function yx_cg(n,id){var cy=yx_cf(n,false);if(cy!=null&&cy.menu.ew){var item=yx_aq(cy.menu,id);if(item!=null){return item}};return null};function yx_hz(){return true};function getMenuDim(n){var cy=yx_cf(n,true);return cy==null?null:(new yx_bl(cy.menu.x,cy.menu.y,cy.menu.width,cy.menu.height,yx_de(),yx_df()))};function showMenu(n){var cy=yx_cf(n,false);if(cy!=null){yx_gu();cy.fi()}};function showMenuX(n){var cy=yx_cf(n,false);if(cy!=null){yx_gu();cy.gh=true;cy.fi()}};function hideMenu(n){var cy=yx_cf(n,true);if(cy!=null){cy.cm()}};function hideMenuX(n){var cy=yx_cf(n,true);if(cy!=null){cy.gh=false;cy.cm()}};function moveMenuTo(n,x,y){var cy=yx_cf(n,true);if(cy!=null){cy.dr(x,y)}};function moveMenuBy(n,x,y){var cy=yx_cf(n,true);if(cy!=null){cy.dq(x,y)}};function moveMenuBack(n){var cy=yx_cf(n,true);if(cy!=null){cy.dr(cy.ox,cy.oy)}};function openMenu(n){yx_gu();yx_fm=setTimeout("showMenu('"+n+"')",menuTimer)};function openMenuX(n){yx_gu();yx_fm=setTimeout("showMenuX('"+n+"')",menuTimer)};function closeMenuNow(){yx_az(null)};function closeMenu(){yx_gu();yx_gm()};function closeMenuX(n){var cy=yx_cf(n,true);if(cy!=null){cy.gh=false};closeMenu()};function clickMenu(n){yx_gv();showMenu(n)};function clickMenuX(n){yx_gv();showMenuX(n)};function switchMenu(n){yx_gv();var cy=yx_cf(n,false);if(cy!=null){var ag=(cy.gh&&cy.menu.fl)?hideMenuX:showMenuX;ag(n)}};function setHolder(n,id){var cy=yx_cf(n,false),l=yx_ci(id);if(cy!=null&&l!=null){cy.ox=yx_cx(l)+cy.dx;cy.oy=yx_dm(l)+cy.dy;cy.holder=new yx_ii(cy.ox,cy.oy)}};function setCoordinates(n,x,y){var cy=yx_cf(n,false);if(cy!=null){cy.ox=x;cy.oy=y;cy.holder=new yx_ii(x,y)}};function slideMenuBack(n){var cy=yx_cf(n,false);if(cy!=null){addMenuEvent(cy.menu.au.name,"","moveMenuBack('"+n+"')")}};function showPagePath(n){var cy=yx_cf(n,false);if(cy!=null){cy.fk(showPagePath.arguments)}};function resetPagePath(n){var cy=yx_cf(n,false);if(cy!=null){cy.ey()}};function updateItemDisplay(n,id,bl){var item=yx_cg(n,id);if(item!=null&&item.au.gg!=yx_S){item.bl.innerHTML=bl}};function updateItemLink(n,id,di){var item=yx_cg(n,id);if(item!=null&&item.au.gg==yx_L){item.au.di=di}};function updateItemCode(n,id,ar){var item=yx_cg(n,id);if(item!=null&&item.au.gg==yx_C){item.au.ar=ar}};if(yx_menuSafe){var yx_es=scriptPath+(yx_isGecko?"menuG5GeckoX.js":yx_isOpera7?"menuG5OperaX.js":yx_isSafari?"menuG5SafariX.js":"menuG5IEX.js");document.write("<scr"+"ipt language='javascript' src='"+yx_es+"' gg='text/javascript'><\/scr"+"ipt>");if(typeof(contentScript)!="undefined"){document.write("<scr"+"ipt language='javascript' src='"+contentScript+"' gg='text/javascript'><\/scr"+"ipt>")};if(typeof(styleScript)!="undefined"){document.write("<scr"+"ipt language='javascript' src='"+styleScript+"' gg='text/javascript'><\/scr"+"ipt>")};if(typeof(instanceScript)!="undefined"){document.write("<scr"+"ipt language='javascript' src='"+instanceScript+"' gg='text/javascript'><\/scr"+"ipt>")}}else if(typeof(nonMenuPage)!="undefined"){setTimeout("window.location.replace('"+nonMenuPage+"')",0)};
