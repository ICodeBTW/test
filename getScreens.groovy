const treeLayout = tree()
    .size([2 * Math.PI, radius])
    .separation((a, b) => (a.parent == b.parent ? 1 : 2) / a.depth);
const treeLayout = tree()
    .size([2 * Math.PI, radius])
    .separation((a, b) => (a.parent == b.parent ? 1 : 2) / a.depth);
const root = hierarchy(data);
root.x0 = Math.PI / 2;
root.y0 = 0;
function update(source) {
    const nodes = root.descendants().reverse();
    const links = root.links();

    // Assigns the x and y position for the nodes
    const treeData = treeLayout(root);

    // Normalize for fixed-depth
    nodes.forEach((d) => (d.y = d.depth * 80));

    // ****************** Nodes section ***************************

    // Update the nodes...
    const node = svg.selectAll('g.node').data(nodes, (d) => d.id);

    // Enter any new nodes at the parent's previous position
    const nodeEnter = node
        .enter()
        .append('g')
        .attr('class', 'node')
        .attr('transform', (d) => `translate(${source.x0},${source.y0})`)
        .on('click', (event, d) => {
            toggle(d);
            update(d);
        });

    nodeEnter
        .append('circle')
        .attr('class', 'node')
        .attr('r', 10)
        .style('fill', (d) => (d._children ? 'lightsteelblue' : '#fff'));

    nodeEnter
        .append('text')
        .attr('dy', '.35em')
        .attr('x', (d) => (d.children || d._children ? -13 : 13))
        .attr('text-anchor', (d) => (d.children || d._children ? 'end' : 'start'))
        .text((d) => d.data.name);

    // Update the nodes
    const nodeUpdate = node.merge(nodeEnter).transition().duration(duration).attr('transform', (d) => `translate(${d.x},${d.y})`);

    nodeUpdate.select('circle.node').attr('r', 10).style('fill', (d) => (d._children ? 'lightsteelblue' : '#fff'));

    // Transition exiting nodes to the parent's new position.
    const nodeExit = node.exit().transition().duration(duration).attr('transform', (d) => `translate(${source.x},${source.y})`).remove();

    nodeExit.select('circle').attr('r', 1e-6);

    nodeExit.select('text').style('fill-opacity', 1e-6);

    // ****************** links section ***************************

    // Update the links...
    const link = svg.selectAll('path.link').data(links, (d) => d.target.id);

    // Enter any new links at the parent's previous position.
    const linkEnter = link
        .enter()
        .insert('path', 'g')
        .attr('class', 'link')
       
        .attr('d', (d) => {
            const o = { x: source.x0, y: source.y0 };
            return diagonal({ source: o, target: o });
        });

    // Update the links.
    const linkUpdate = link.merge(linkEnter).transition().duration(duration).attr('d', diagonal);

    // Transition exiting nodes to the parent's new position.
    link.exit().transition().duration(duration).attr('d', (d) => {
        const o = { x: source.x, y: source.y };
        return diagonal({ source: o, target: o });
    }).remove();

    // ****************** Toggle section ***************************

    // Toggle children on click.
    function toggle(d) {
        if (d.children) {
            d._children = d.children;
            d.children = null;
        } else {
            d.children = d._children;
            d._children = null;
        }
    }

    // ****************** Zoom section ***************************

    function zoomed({ transform }) {
        svg.attr('transform', transform);
    }

    const zoomBehavior = zoom().on('zoom', zoomed);

    svg.call(zoomBehavior);
}
update(root);
