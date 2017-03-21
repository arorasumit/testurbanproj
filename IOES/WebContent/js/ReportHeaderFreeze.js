function reposHead(e) {
    var h = document.getElementById('headscroll');
    h.scrollLeft = e.scrollLeft;
    var f = document.getElementById('divfrozen');
    f.scrollTop = e.scrollTop;
}
function reposHorizontal(e) {
    var h = document.getElementById('headscroll');
    var c = document.getElementById('contentscroll');
    h.scrollLeft = e.scrollLeft;
    c.scrollLeft = e.scrollLeft;
}
function reposVertical(e) {
    var h = document.getElementById('divfrozen');
    var c = document.getElementById('contentscroll');
    h.scrollTop = e.scrollTop;
    c.scrollTop = e.scrollTop;
}