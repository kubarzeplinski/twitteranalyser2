import {connect} from "react-redux";
import Controls from "./Controls";
import {handleKeywordChange, handleSearchButtonClick} from "../../redux/graph-controls";

function mapStateToProps(state) {
    const {isKeywordInputBlocked, isSearchButtonBlocked, keyword} = state.graphControls;
    return {
        isKeywordInputBlocked,
        isSearchButtonBlocked,
        keyword
    };
}

function mapDispatchToProps(dispatch) {
    return {
        handleKeywordChange(keyword) {
            dispatch(handleKeywordChange(keyword));
        },
        handleSearchButtonClick() {
            dispatch(handleSearchButtonClick());
        }
    };
}

const ControlsContainer = connect(
    mapStateToProps,
    mapDispatchToProps,
)(Controls);

export default ControlsContainer;
