import {connect} from "react-redux";
import Controls from "./Controls";
import {handleKeywordAdd, handleRunButtonClick, handleStopButtonClick, initWebSocket} from "../../redux/controls";

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
        handleKeywordAdd(keyword) {
            dispatch(handleKeywordAdd(keyword));
        },
        handleRunButtonClick() {
            dispatch(handleRunButtonClick());
        },
        handleStopButtonClick() {
            dispatch(handleStopButtonClick());
        },
        initWebSocket(){
            dispatch(initWebSocket());
        },
    };
}

const ControlsContainer = connect(
    mapStateToProps,
    mapDispatchToProps,
)(Controls);

export default ControlsContainer;
