import { html } from "@polymer/lit-element";
import { PageViewElement } from "../page-view-element.js";

import 'mdbootstrap';
import {LoginStyle} from "../login-style";

class LoginView extends PageViewElement {
  handleSubmit(event) {
    event.preventDefault();
    const data = {};
    new FormData(event.target).forEach(function(value, key) {
      data[key] = value;
    });
    data.password = sha256(data.password);
    const url = event.target.getAttribute("action");
    const json = JSON.stringify(data);
    fetch(url, {
      method: "post",
      body: json,
      credentials: "include",
      headers: {
        "content-type": "application/json"
      }
    });
  }

  /*
  * ${SharedStyles}
      <section>
        <div>Login</div>
        <form
          action="http://localhost:8080/iea/rs/auth/login"
          @submit="${event => this.handleSubmit(event)}">
          <input type="text" name="username" />
          <input type="password" name="password" />
          <input type="submit" value="Send" />
        </form>
      </section>
  * */


  render() {
    return html`
      ${LoginStyle}
      <link rel="stylesheet" href="../../../node_modules/mdbootstrap/css/bootstrap.min.css">
      
      <div>
        <div style="width: 50%; position: absolute; border-right: 1px solid #efefef;">
          <form class="text-center p-5">
            <p class="h4 mb-4">Sign up</p>
        
            <div class="form-row mb-4">
                <div class="col">
                    <!-- First name -->
                    <input type="text" id="defaultRegisterFormFirstName" class="form-control" placeholder="First name">
                </div>
                <div class="col">
                    <!-- Last name -->
                    <input type="text" id="defaultRegisterFormLastName" class="form-control" placeholder="Last name">
                </div>
            </div>
            <input type="email" id="defaultRegisterFormEmail" class="form-control mb-4" placeholder="E-mail">
            <input type="password" id="defaultRegisterFormPassword" class="form-control" placeholder="Password" aria-describedby="defaultRegisterFormPasswordHelpBlock">
            <small id="defaultRegisterFormPasswordHelpBlock" class="form-text text-muted mb-4">
                At least 8 characters and 1 digit
            </small>
        
            <!-- Newsletter -->
            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" id="defaultRegisterFormNewsletter">
                <label class="custom-control-label" for="defaultRegisterFormNewsletter">Subscribe to our newsletter</label>
            </div>
        
            <button class="btn btn-info my-4 btn-block" type="submit">Sign up</button>
            <hr>
            
            <p>By clicking
                <em>Sign up</em> you agree to our
                <a href="" target="_blank">terms of service</a></p>
                
          </form>
        </div>
        <div style="width: 50%; left: 0; right: 0; display: inline-block; float: right;">
          <form class="text-center p-5" action="http://localhost:8080/iea/rs/auth/login"
            @submit="${event => this.handleSubmit(event)}">
            <p class="h4 mb-4">Sign in</p>
            <input type="email" id="defaultLoginFormEmail" class="form-control mb-4" placeholder="E-mail">
            <input type="password" id="defaultLoginFormPassword" class="form-control mb-4" placeholder="Password">
            <button class="btn btn-info btn-block my-4" type="submit">Sign in</button>
          </form>
        </div>
      </div>
    `;
  }
}

window.customElements.define("login-view", LoginView);
