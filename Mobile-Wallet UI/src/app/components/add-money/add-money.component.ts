import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { card } from 'src/app/models/card';
import { WalletService } from 'src/app/services/Wallet.service';

@Component({
  selector: 'app-add-money',
  templateUrl: './add-money.component.html',
  styleUrls: ['./add-money.component.css']
})
export class AddMoneyComponent implements OnInit {
  cards: card[];
  emptyy: number = 1;
  message: any;
  temp1: boolean;
  temp: boolean;

  constructor(private service: WalletService, private router: Router) { }

  ngOnInit() {
    if (localStorage.getItem("token") === null) {
      alert("Session Expired..!! Please login or signup");
      this.router.navigate(['loginByNumber']);
    }
    this.checkBalance();
    this.getCards();

  }
  checkBalance() {
    let b = this.service.check(localStorage.token);
    b.subscribe(data => {
      this.message = data
    },
      err => {
        console.log(err);
      }
    );
  }
  getCards() {
    this.service.viewCards(localStorage.token).subscribe(data => {
      this.cards = data;
      this.emptyy = this.cards.length;
      if (this.emptyy == 0) {

        this.temp = false;
        this.temp1 = true;
      }
      else {
        this.temp = true;
        this.temp1 = false;
      }
    },
      err => {
        console.log(err.stack);
      })
  }
  addMoney(cardNumber) {
    localStorage.card = cardNumber;
    this.router.navigate(['enter-amount']);
  }
  addCard() {
    this.router.navigate(['add-card']);
  }

}
