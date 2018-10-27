import {connect} from "react-redux";
import _ from "lodash";
import Graph from "./Graph";
import {
    handleRelationDataFetch,
    handleRelationDataFetched,
    handleRelationDialogOpen,
    handleUserDataFetch,
    handleUserDialogOpen
} from "../../redux/graph-page";

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
            fetch('http://localhost:8080/graphs/users/' + screenName, {
                method: 'GET',
                headers: {'Content-Type': 'application/json'}
            })
                .then((response) => response.json())
                .then((response) => dispatch(handleUserDataFetch(response)))
                .then(() => dispatch(handleUserDialogOpen()));
        },
        handleLinkClick(event) {
            fetch('http://localhost:8080/graphs/relation', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    keyword: event.keyword,
                    source: event.source.name,
                    target: event.target.name,
                    type: event.type
                })
            })
                .then((response) => response.json())
                .then((response) => dispatch(handleRelationDataFetch(response)))
                .then(() => dispatch(handleRelationDialogOpen()));
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
