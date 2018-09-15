import {combineReducers} from "redux";
import graphControls from "../pages/graph/redux/graph-controls";
import graphPage from "../pages/graph/redux/graph-page";
import statisticsControls from "../pages/statistics/redux/statistics-controls";
import statisticsPage from "../pages/statistics/redux/statistics-page";

export default function rootReducer(state = {}, action) {

    return combineReducers({
        graphControls,
        graphPage,
        statisticsControls,
        statisticsPage
    })(state, action);

}
