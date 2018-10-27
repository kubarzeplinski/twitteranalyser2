import {connect} from "react-redux";
import {
    handleInfoButtonClick,
    handleInfoDialogClose,
    handleRelationDialogClose,
    handleUserDialogClose
} from "./redux/graph-page";
import GraphPage from "./GraphPage";
import {handleKeywordsFetch} from "./redux/graph-controls";

function mapStateToProps(state) {
    const {
        isInfoDialogOpen,
        isRelationDialogOpen,
        isUserDialogOpen,
        relationData,
        userData
    } = state.graphPage;
    return {
        isInfoDialogOpen,
        isRelationDialogOpen,
        isUserDialogOpen,
        relationData,
        userData
    };
}

function mapDispatchToProps(dispatch) {
    return {
        handleInfoButtonClick() {
            dispatch(handleInfoButtonClick());
        },
        handleInfoDialogClose() {
            dispatch(handleInfoDialogClose());
        },
        handleKeywordsFetch() {
            fetch('http://localhost:8080/graphs/keywords', {
                method: 'GET',
                headers: {'Content-Type': 'application/json'}
            })
                .then((response) => response.json())
                .then((response) => {
                    dispatch(handleKeywordsFetch(response));
                });
        },
        handleRelationDialogClose() {
            dispatch(handleRelationDialogClose())
        },
        handleUserDialogClose() {
            dispatch(handleUserDialogClose());
        }
    };
}

const GraphPageContainer = connect(
    mapStateToProps,
    mapDispatchToProps,
)(GraphPage);

export default GraphPageContainer;
