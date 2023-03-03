var zoom = d3.behavior.zoom()
    .scaleExtent([0.5, 10]) // set the minimum and maximum zoom scale
    .on("zoom", function () {
        // get the current zoom transform
        var transform = d3.event.translate + " " + d3.event.scale;
        // apply the zoom transform to the SVG elements
        svg.attr("transform", transform);
    });
