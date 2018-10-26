import {connect} from "react-redux";
import _ from "lodash";
import Graph from "./Graph";
import {handleUserDataFetch, handleUserDialogOpen} from "../../redux/graph-page";

function mapStateToProps(state) {
    const {data, isDataLoading} = state.graphControls;
    return {
        data: prepareData(data),
        isDataLoading
    };
}

function mapDispatchToProps(dispatch) {
    return {
        handleNodeClick(screenName) {
            fetch('http://localhost:8080/graph/user/' + screenName, {
                method: 'GET',
                headers: {'Content-Type': 'application/json'}
            })
                .then((response) => response.json())
                .then((response) => dispatch(handleUserDataFetch(response)))
                .then(() => dispatch(handleUserDialogOpen()));
        }
    };
}

function prepareData(data) {
    if (_.isNil(data)) {
        return {
            links: [],
            nodes: [],
            sentimentStatistics: {
                negativeUsers: 0,
                neutralUsers: 0,
                positiveUsers: 0,
                veryNegativeUsers: 0,
                veryPositiveUsers: 0
            }
        };
    }
    return data;
}

const GraphContainer = connect(
    mapStateToProps,
    mapDispatchToProps,
)(Graph);

export default GraphContainer;
