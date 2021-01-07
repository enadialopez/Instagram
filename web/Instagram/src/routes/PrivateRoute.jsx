import React from "react";
import { Redirect, Route } from "react-router-dom";
import axios from "axios";

const PrivateRoute = ({ path, component }) => {
  const isAuthenticated = !!localStorage.getItem("token");

  if (!isAuthenticated) return <Redirect to={"/login"}  />
  axios.defaults.headers['authorization'] = localStorage.getItem('token');

  return <Route path={path} component={component} />;
};

export default PrivateRoute;