import {connect} from "react-redux";
import _ from "lodash";
import Graph from "./Graph";

function mapStateToProps(state) {
    const {data, isDataLoading} = state.graphControls;
    return {
        links: prepareData(data),
        isDataLoading
    };
}

function mapDispatchToProps(dispatch) {
    return {};
}

function prepareData(data) {
    if (_.isNil(data)) {
        return [];
    }
    return data.links;
}

const GraphContainer = connect(
    mapStateToProps,
    mapDispatchToProps,
)(Graph);

export default GraphContainer;
