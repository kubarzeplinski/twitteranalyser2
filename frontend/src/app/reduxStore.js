import createHistory from "history/createBrowserHistory";
import {routerMiddleware} from "react-router-redux";
import {applyMiddleware, createStore} from "redux";
import {composeWithDevTools} from "redux-devtools-extension";
import thunkMiddleware from "redux-thunk";
import rootReducer from "../reducers/rootReducer";

export default function configureStore() {
    const history = createHistory();
    const reduxMiddleware = [
        thunkMiddleware,
        routerMiddleware(history),
    ];
    // https://github.com/zalmoxisus/redux-devtools-extension
    const composeEnhancers = composeWithDevTools({
        // Specify name here, actionsBlacklist, actionsCreators and other options if needed
    });
    const store = createStore(
        rootReducer,
        composeEnhancers(
            applyMiddleware(...reduxMiddleware),
        ),
    );

    if (module.hot) {
        // Enable Webpack hot module replacement for reducers
        module.hot.accept("../reducers/rootReducer", () => {
            const newModule = require("../reducers/rootReducer");
            store.replaceReducer(newModule.default);
        });
    }

    return {
        store,
        history,
    };

}
