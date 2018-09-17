import {connect} from "react-redux";
import {handleInfoButtonClick, handleInfoDialogClose} from "./redux/graph-page";
import GraphPage from "./GraphPage";
import {handleKeywordsFetch} from "./redux/graph-controls";

function mapStateToProps(state) {
    const {isInfoDialogOpen} = state.graphPage;
    return {
        isInfoDialogOpen
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
            fetch('http://localhost:8080/graph/keywords', {
                method: 'GET',
                headers: {'Content-Type': 'application/json'}
            })
                .then((response) => response.json())
                .then((response) => {
                    dispatch(handleKeywordsFetch(response));
                });
        }
    };
}

const GraphPageContainer = connect(
    mapStateToProps,
    mapDispatchToProps,
)(GraphPage);

export default GraphPageContainer;
