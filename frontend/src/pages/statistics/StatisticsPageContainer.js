import {connect} from "react-redux";
import {handleInfoButtonClick, handleInfoDialogClose} from "./redux/statistics-page";
import StatisticsPage from "./StatisticsPage";

function mapStateToProps(state) {
    const {isInfoDialogOpen} = state.statisticsPage;
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

const StatisticsPageContainer = connect(
    mapStateToProps,
    mapDispatchToProps,
)(StatisticsPage);

export default StatisticsPageContainer;
