import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import Notifications,{notify} from 'react-notify-toast';
import '../../styles/Login.css';
import logo from '../../images/fotoInstagram.png';
import logoig from '../../images/instagram-new-logo.png' ;
import axios from "axios";
import Api from "../../api/api";

const Login = () => {
    const history = useHistory();
    const [data, setData] = useState({
      email: "",
      password: "",
    });

    let myColor = { background: '#0E1717', text: "#FFFFFF" };

    const handleInputChange = (event) => {
        setData({
          ...data,
          [event.target.name]: event.target.value,
        });
      };

    const goRegister = () => {
        history.push("/register") ;
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        Api.login(data)
            .then((response) => {    
                localStorage.setItem("token", response.headers.authorization);
                history.push("/home");       
          })
            .catch(error => {
                console.log("error : ", error.response.data.message);
                const errorUser = error.response.data.message ;
                notify.show(errorUser,"error",5000,myColor);          
        });
        
      };



    return (
        <div className="container-fluid">
            <Notifications />
            <div className="row">
                <div id="columnaIzquierda" className="col-md-6">
                    <div id="fotoInstagram">
                        <div className="foto">
                        <img src={logo} className="celu"/>
                        </div> 
                    </div>
                </div>
                
                <div id="columnaDerecha" className="col-md-3 text-center">            
                    <img src={logoig} className="fontIg"/>
                        <form onSubmit={handleSubmit}>
                            <div id="login">
                                <div className="form-group" >
                                    <input className="form-control" type="text" name="email" value={data.email} onChange={handleInputChange} placeholder="Email" required />
                                </div>
                                <div className="form-group" >
                                    <input className="form-control" type="password" name="password" value={data.password} onChange={handleInputChange} placeholder="Password" required />
                                </div>
                                <div className="button">
                                    <button type="submit" className="btn btn-primary btn-lg btn-block">Iniciar sesion</button>
                                </div>
                            </div>
                        </form>
                        <form onSubmit={goRegister}>
                            <div id="register">
                                <h6>¿ No tenes cuenta ?</h6>
                                <button type="submit" className="btn btn-link">Registrate</button>
                            </div>
                        </form>               
                </div>
            </div>
        </div>
                            
    )
}

export default Login ;