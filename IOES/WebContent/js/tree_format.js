var TREE_FORMAT =
[
//0. left position
	0,
//1. top position
	5,
//2. show +/- buttons
	true,
//3. couple of button images (collapsed/expanded/blank)
	["gifs/c.gif", "gifs/e.gif", "gifs/b.gif"],
//4. size of images (width, height,ident for nodes w/o children)
	[16,16,16],
//5. show folder image
	true,
//6. folder images (closed/opened/document)
	["gifs/fc.gif", "gifs/fe.gif", "gifs/i.gif"],
//7. size of images (width, height)
	[16,16],
//8. identation for each level [0/*first level*/, 16/*second*/, 32/*third*/,...]
	[0,16,32,48],
//9. tree background color ("" - transparent)
	"",
//10. default style for all nodes
	"clsNode",
//11. styles for each level of menu (default style will be used for undefined levels)
	[],//["clsNodeL0","clsNodeL1","clsNodeL2","clsNodeL3","clsNodeL4"],
//12. true if only one branch can be opened at same time
	false,
//13. item pagging and spacing
	[0,2],
];
