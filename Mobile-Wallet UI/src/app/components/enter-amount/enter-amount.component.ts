import { Component, OnInit } from '@angular/core';
import { WalletService } from 'src/app/services/Wallet.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-enter-amount',
  templateUrl: './enter-amount.component.html',
  styleUrls: ['./enter-amount.component.css']
})
export class EnterAmountComponent implements OnInit {
  addmoney: FormGroup;
  submitted:boolean=false;
  cardn:number;
  cvv1:number;

  constructor(private formBuilder: FormBuilder,private walletService: WalletService, private router: Router) { }

  ngOnInit() {
    if (localStorage.getItem("token") === null) 
    {
      alert("Session Expired..!! Please login or signup");
      this.router.navigate(['loginByNumber']);
    }
    this.addmoney = this.formBuilder.group({
      amount: ['', [Validators.required,Validators.pattern('[0-9]{1,4}')]],
      cvv:['',[Validators.required,  Validators.pattern('[0-9]{3}')]]

      });
  }

  addMoney()
  {
    this.submitted = true;
    this.cvv1=localStorage.cvv;

    if (this.addmoney.invalid) {
      return;
    }
    if(this.addmoney.value.cvv!=this.cvv1)
    {
      alert('wrong cvv')
      return;
    }

   
    this.cardn=localStorage.card;
   alert(this.cardn);
    console.log(this.addmoney.value.amount);
    console.log(this.cardn);
    this.walletService.addMoney(localStorage.token,this.cardn,this.addmoney.value.amount).subscribe(data => {
      alert(`you've added money succesfully`);
      this.router.navigate(['/home']);
    },
      err => {
        console.log(err.stack);

      })
  }

}
