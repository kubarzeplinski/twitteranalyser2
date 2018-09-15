import {connect} from "react-redux";
import MainNavbar from "./MainNavbar";

function mapStateToProps(state) {
    const {isProcessingOn, keyword} = state.statisticsControls;
    return {
        isProcessingOn,
        keyword
    };
}

function mapDispatchToProps(dispatch) {
    return {};
}

const MainNavbarContainer = connect(
    mapStateToProps,
    mapDispatchToProps,
)(MainNavbar);

export default MainNavbarContainer;
