import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { Validators } from '@angular/forms';
import { WalletService } from 'src/app/services/wallet.service';
import { PasswordValidator } from 'src/app/shared/password.validator';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  changePasswordForm: FormGroup;
  submitted: boolean = false;
  errorMessage = '';
  constructor(private formBuilder: FormBuilder,
    private router: Router, private location: Location,private service: WalletService) { }

  ngOnInit() {
    if (localStorage.getItem("token") === null) 
    {
      alert("Session Expired..!! Please login or signup");
      this.router.navigate(['loginByNumber']);
    }
    this.changePasswordForm = this.formBuilder.group({
      oldPassword:['',Validators.required],
      newPassword: ['', [Validators.required,Validators.pattern("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}")]],
      confirmPassword: ['', Validators.required]
  },{ validators : PasswordValidator});
}
  cancel() {
    this.location.back();
  }

  verifyAndChangePassword() {
    this.submitted = true;
    if(this.changePasswordForm.invalid)
    return;
    let oldPassword = this.changePasswordForm.controls.oldPassword.value;
    let newPassword = this.changePasswordForm.controls.newPassword.value;

    this.service.changePassword(localStorage.token, oldPassword, newPassword).subscribe(data=>{
     if(data=="Changed Password successfully")
     {
      alert(`Password Changed Successfully...!`);
      this.router.navigate(["/home"])
     }
     else
     {
       alert('Enter correct current password');
     }
         
     
    },err=>{
      console.log(err)
      if(err.error=="Session Expired"){
        localStorage.removeItem("token");
        alert("Session Expired....Login Again");
      this.router.navigate(["/loginByNumber"])
      }
      
    });
    

  }
}
