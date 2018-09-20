import * as d3 from "d3";

export function prepareGraphContentData(data, sizeProperty, colors) {
    if (_.isEmpty(data)) {
        return [];
    }
    let maxSize = 0;
    let minSize = 0;
    if (_.find(data, (e) => !_.isNil(e[sizeProperty]))) {
        maxSize = _.maxBy(data, sizeProperty)[sizeProperty];
        minSize = _.minBy(data, sizeProperty)[sizeProperty];
    }
    if (_.find(data, (e) => _.isNil(e[sizeProperty]))) {
        minSize = 0;
    }
    const minR = 15;
    const maxR = 50;
    const minFontSize = 10;
    const maxFontSize = 20;
    const scaleRadius = d3.scaleSqrt().domain([minSize, maxSize]).range([minR, maxR]);
    const scaleLabelFontSize = d3.scaleLinear().domain([minSize, maxSize]).range([minFontSize, maxFontSize]);
    const scaleTargetHorizontalPositionFactor = prepareTargetHorizontalPositionScaleFactor(data, 0.33);
    return data.map((e) => {
        const labelBaseFontSize = scaleLabelFontSize(e[sizeProperty]);
        return {
            ...e,
            label: e.source,
            labelColor: colors.labelColor,
            labelBaseFontSize,
            labelFontSizeEm: [0.8, 0.5, 0.5, 0.8],
            labelYPositionEmOffset: [-1, -0.4, 0.8, 1.6],
            color: colors.backgroundColor,
            hoverColor: colors.hoverBackgroundColor,
            opacityColor: colors.opacityBackgroundColor,
            targetHorizontalPositionFactor: scaleTargetHorizontalPositionFactor(0),
            radius: scaleRadius(e[sizeProperty])
        };
    });
}

function prepareTargetHorizontalPositionScaleFactor(nodes, step) {
    const domain = _.chain(nodes)
        .map((e) => 0)
        .sortBy()
        .value();
    const limit = (domain.length - 1) * step;
    return d3.scaleOrdinal()
        .domain(domain)
        .range(_.range(-limit, limit + step, 2 * step));
}