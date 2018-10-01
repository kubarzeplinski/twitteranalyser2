import "./graph-navigation.scss";

import React from "react";
import PropTypes from "prop-types";
import {Icon} from "@blueprintjs/core";
import {IconNames} from "@blueprintjs/icons";

export default class GraphNavigation extends React.Component {

    static propTypes = {
        onUpClick: PropTypes.func,
        onLeftClick: PropTypes.func,
        onRightClick: PropTypes.func,
        onDownClick: PropTypes.func,
        onPlusClick: PropTypes.func,
        onMinusClick: PropTypes.func
    };

    render() {
        return (
            <div className="graph-navigation">
                <Icon
                    iconName={IconNames.ZOOM_IN}
                    iconSize={Icon.SIZE_LARGE}
                    onClick={this.props.onPlusClick}
                />
                <Icon
                    iconName={IconNames.ZOOM_OUT}
                    iconSize={Icon.SIZE_LARGE}
                    onClick={this.props.onMinusClick}
                />
            </div>
        );
    }

}