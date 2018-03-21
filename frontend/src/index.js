import "babel-polyfill";
import * as React from "react";
import * as ReactDOM from "react-dom";
import {Provider} from "react-redux";
import {ConnectedRouter} from "react-router-redux";
import configureStore from "./app/reduxStore";
import "./scss/app.scss";
import App from "./app/App";

export const {store, history} = configureStore();
const root = document.getElementById('root');

const render = () => {
    ReactDOM.render(
        <Provider store={store}>
            <ConnectedRouter history={history}>
                <App/>
            </ConnectedRouter>
        </Provider>,
        root,
    );
};

if (module.hot) {
    module.hot.accept('./app/App', () => {
        setTimeout(render);
    });
}

render();
