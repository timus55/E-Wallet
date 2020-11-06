import { Route } from '@angular/compiler/src/core';
import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { WalletService } from 'src/app/services/wallet.service';


@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  forgotPassForm: FormGroup;
  msg:String
  submitted: boolean = false;
  securityQuestion=[
    "Your favourite Language? ",
    "your favourite superhero of any movie or any comic? ",
    "Your hobbie? ",
    "Your best friend? ",
    "Your pet name? "
   ]
  constructor(private formBuilder:FormBuilder, private router:Router, private service:WalletService) { }

  ngOnInit() {
    this.forgotPassForm = this.formBuilder.group({
      mobile: ['', [Validators.required, Validators.minLength(10),Validators.maxLength(10), Validators.pattern('[6-9]{1}[0-9]+')]],
      securityQ: ['', Validators.required],
      securityA: ['', Validators.required]
    });
  }
  resetPassword(){
    this.submitted= true;
    if (this.forgotPassForm.invalid) {
      return;
    }
    let mobile = this.forgotPassForm.controls.mobile.value;
    let securityQ = this.forgotPassForm.controls.securityQ.value;
    let securityA = this.forgotPassForm.controls.securityA.value;
   
  
    this.service.forgotPassword(mobile,securityQ,securityA).subscribe(data => {
      
      if(data == "Invalid securityQuestion/Answer"){
        alert("Incorrect Security Question or Answer")
      }
      else{
        alert("Your temporary  password is  " + data +" Please login through this password and change it for security purpose");
        localStorage.clear();
        this.router.navigate(['/loginByNumber']);
      }
    
    },err => {
    console.log(err.error);
    alert("The mobile doesn't Exist");
    
  });

  }

}
