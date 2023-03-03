function diagonal(d) {
  const sourceX = d.source.x;
  const sourceY = d.source.y;
  const targetX = d.target.x;
  const targetY = d.target.y;

  const r = Math.sqrt(sourceX * sourceX + sourceY * sourceY);
  const radialSource = [sourceY, sourceX / r * 180 / Math.PI];
  const radialTarget = [targetY, targetX / r * 180 / Math.PI];

  const diagonalPath = d3.linkRadial()
    .angle((d) => d[1])
    .radius((d) => d[0])
    ({"source": radialSource, "target": radialTarget});

  return diagonalPath;
}
