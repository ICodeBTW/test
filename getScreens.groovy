function update(source) {
  // Compute the new tree layout.
  const updatedData = treeLayout(root);

  // Update the links.
  const link = g.selectAll("path.link")
    .data(updatedData.links());
  
  link.enter()
    .append("path")
    .attr("class", "link")
    .merge(link)
    .transition()
    .duration(duration)
    .attr("d", link);
  
  link.exit().remove();

  // Update the nodes.
  const node = g.selectAll("g.node")
    .data(updatedData.descendants(), d => d.id);
  
  const nodeEnter = node.enter()
    .append("g")
    .attr("class", "node")
    .attr("transform", d => `rotate(${source.x0 * 180 / Math.PI - 90}) translate(${source.y0},0)`)
    .on("click", d => {
      d.children = d.children ? null : d._children;
      update(d);
    });
  
  nodeEnter.append("circle")
    .attr("r", 1e-6)
    .style("fill", d => d._children ? "#ccc" : "#fff");

  nodeEnter.append("text")
    .attr("dy", "0.31em")
    .attr("x", d => d.x < Math.PI === !d.children ? 6 : -6)
    .attr("text-anchor", d => d.x < Math.PI === !d.children ? "start" : "end")
    .attr("transform", d => d.x < Math.PI ? null : "rotate(180)")
    .text(d => d.data.name)
    .clone(true)
    .lower()
    .attr("stroke-linejoin", "round")
    .attr("stroke-width", 3)
    .attr("stroke", "white");
  
  const nodeUpdate = nodeEnter.merge(node);
  
  nodeUpdate.transition()
    .duration(duration)
    .attr("transform", d => `rotate(${d.x * 180 / Math.PI - 90}) translate(${d.y},0)`);
  
  nodeUpdate.select("circle")
    .attr("r", 4.5)
    .style("fill", d => d._children ? "#ccc" : "#fff");
  
  node.exit().transition()
    .duration(duration)
    .attr("transform", d => `rotate(${source.x * 180 / Math.PI - 90}) translate(${source.y},0)`)
    .remove();
  
  // Stash the old positions for transition.
  updatedData.each(d => {
    d.x0 = d.x;
    d.y0 = d.y;
  });
}
