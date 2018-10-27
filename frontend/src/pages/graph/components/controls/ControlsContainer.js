import {connect} from "react-redux";
import Controls from "./Controls";
import {handleKeywordChange, handleNewDataFetch, handleSearchButtonClick} from "../../redux/graph-controls";

function mapStateToProps(state) {
    const {keyword, keywords} = state.graphControls;
    return {
        keyword,
        keywords
    };
}

function mapDispatchToProps(dispatch) {
    return {
        handleKeywordChange(keyword) {
            dispatch(handleKeywordChange(keyword));
        },
        handleSearchButtonClick(keyword) {
            dispatch(handleSearchButtonClick());
            fetch('http://localhost:8080/graphs/' + keyword, {
                method: 'GET',
                headers: {'Content-Type': 'application/json'}
            })
                .then((response) => response.json())
                .then((response) => {
                    dispatch(handleNewDataFetch(response));
                });
        }
    };
}

const ControlsContainer = connect(
    mapStateToProps,
    mapDispatchToProps,
)(Controls);

export default ControlsContainer;
