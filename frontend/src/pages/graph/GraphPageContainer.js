import {connect} from "react-redux";
import {handleInfoButtonClick, handleInfoDialogClose} from "./redux/graph-page";
import GraphPage from "./GraphPage";

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
        }
    };
}

const GraphPageContainer = connect(
    mapStateToProps,
    mapDispatchToProps,
)(GraphPage);

export default GraphPageContainer;
