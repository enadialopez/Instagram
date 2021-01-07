import React from "react";
import Login from '../components/Login/Login'
import Register from '../components/Register/Register'
import Home from '../components/Home/Home'
import PublicRoute from './PublicRoute'
import PrivateRoute from './PrivateRoute'
import Profile from '../components/Profile/Profile'
import User from '../components/User/User'
import {
  BrowserRouter as Router,
  Switch,
} from "react-router-dom";


export default function Routes() {
    return (
        <Router>
            <Switch>
                <PublicRoute path="/login" component={Login} />
                <PublicRoute path="/register" component={Register} />
                <PrivateRoute path="/home" component={Home} />
                <PrivateRoute path="/profile" component={Profile} />
                <PrivateRoute path="/user/:id" component={User} />
                <PrivateRoute path="*" component={Home} /> 
            </Switch>

        </Router>
    );
}