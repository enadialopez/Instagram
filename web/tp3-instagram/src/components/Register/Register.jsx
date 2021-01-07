import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import Notifications,{notify} from 'react-notify-toast';
import logoig from '../../images/instagram-new-logo.png' ;
import logo from '../../images/fotoInstagram.png';
import '../../styles/Register.css';
import axios from "axios";
import Api from "../../api/api";


const Register = () => {
    const history = useHistory();
    const [data, setData] = useState({
        name: "",
        email: "",
        password: "",
        image: ""
    });

    let myColor = { background: '#0E1717', text: "#FFFFFF" };

    const handleInputChange = (event) => {
        setData({
            ...data,
            [event.target.name]: event.target.value,
        });
    };

    const goLogin = () => {
        history.push("/login") ;
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        Api.register(data)
            .then((response) => {    
                localStorage.setItem("token", response.headers.authorization);
                axios.defaults.headers['authorization'] = localStorage.getItem('token')
                history.push("/");       
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
                            <h2>Regístrate para ver fotos y videos de tus amigos.</h2>
                            <div id="login">
                                <div className="form-group" >
                                    <input className="form-control" type="text" name="name" value={data.name} onChange={handleInputChange} placeholder="Name" required />
                                </div>
                                <div className="form-group" >
                                    <input className="form-control" type="email" name="email" value={data.email} onChange={handleInputChange} placeholder="Email" required />
                                </div>
                                <div className="form-group" >
                                    <input className="form-control" type="password" name="password" value={data.password} onChange={handleInputChange} placeholder="Password" required />
                                </div>
                                <div className="form-group" >
                                    <input className="form-control" type="text" name="image" value={data.image} onChange={handleInputChange} placeholder="Image" required />
                                </div>
                                <div className="button">
                                    <button type="submit" className="btn btn-primary btn-lg btn-block">Registrarte</button>
                                </div>
                            </div>
                        </form>
                        <form onSubmit={goLogin}>
                            <div id="register">
                                <h6>¿ Ya tenes cuenta ?</h6>
                                <button type="submit" className="btn btn-link">Iniciar Sesion</button>
                            </div>
                        </form>               
                </div>
            </div>
        </div>
                            
    )
}

export default Register ;