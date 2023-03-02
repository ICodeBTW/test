// Set the dimensions and margins of the diagram
var margin = {top: 20, right: 90, bottom: 30, left: 90},
    width = 960 - margin.left - margin.right,
    height = 500 - margin.top - margin.bottom;

// Append an SVG element to the body of the page
var svg = d3.select("body").append("svg")
    .attr("width", width + margin.right + margin.left)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + (margin.left + width / 2) + "," + (margin.top + height / 2) + ")");

// Create a hierarchical data structure
var treeData = {
  "name": "A",
  "children": [
    {
      "name": "B",
      "children": [
        {
          "name": "C"
        },
        {
          "name": "D"
        }
      ]
    },
    {
      "name": "E",
      "children": [
        {
          "name": "F"
        },
        {
          "name": "G"
        }
      ]
    }
  ]
};

// Create a tree layout and set the size of the tree
var treeLayout = d3.tree()
    .size([360, 500])
    .separation(function(a, b) { return (a.parent == b.parent ? 1 : 2) / a.depth; });

// Create a root node and pass in the tree data
var root = d3.hierarchy(treeData);

// Assigns the parent, children, height, and depth properties
root.x0 = height / 2;
root.y0 = 0;

// Compute the layout and get the nodes and links
var treeNodes = treeLayout(root).descendants();
var treeLinks = treeNodes.slice(1);

// Draw the links
svg.selectAll(".link")
    .data(treeLinks)
    .enter().append("path")
    .attr("class", "link")
    .attr("d", function(d) {
      return "M" + project(d.x, d.y)
           + "C" + project(d.x, (d.y + d.parent.y) / 2)
           + " " + project(d.parent.x, (d.y + d.parent.y) / 2)
           + " " + project(d.parent.x, d.parent.y);
    });

// Draw the nodes
var node = svg.selectAll(".node")
    .data(treeNodes)
    .enter().append("g")
    .attr("class", "node")
    .attr("transform", function(d) { return "translate(" + project(d.x, d.y) + ")"; });

node.append("circle")
    .attr("r", 10);

node.append("text")
    .attr("dy", ".31em")
    .attr("x", function(d) { return d.x < 180 === !d.children ? 6 : -6; })
    .style("text-anchor", function(d) { return d.x < 180 === !d.children ? "start" : "end"; })
    .attr("transform", function(d) { return "rotate(" + (d.x < 180 ? d.x - 90 : d.x + 90) + ")"; })
    .
