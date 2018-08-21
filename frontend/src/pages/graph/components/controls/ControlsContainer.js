import {connect} from "react-redux";
import Controls from "./Controls";
import {handleKeywordChange, handleRunButtonClick, handleStopButtonClick} from "../../redux/controls";

function mapStateToProps(state) {
    const {isKeywordInputBlocked, isRunButtonBlocked, isStopButtonBlocked, keyword} = state.controls;
    return {
        isKeywordInputBlocked,
        isRunButtonBlocked,
        isStopButtonBlocked,
        keyword,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        handleKeywordChange(keyword) {
            dispatch(handleKeywordChange(keyword));
        },
        handleRunButtonClick() {
            dispatch(handleRunButtonClick());
        },
        handleStopButtonClick() {
            dispatch(handleStopButtonClick());
        }
    };
}

const ControlsContainer = connect(
    mapStateToProps,
    mapDispatchToProps,
)(Controls);

export default ControlsContainer;
