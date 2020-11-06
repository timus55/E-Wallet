import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Transaction } from '../models/transaction';
import { UserInfo } from '../models/user';
import { card } from 'src/app/models/card';


@Injectable({
  providedIn: 'root'
  
})
export class WalletService {
  static userList:UserInfo;
  baseUrl: string = "http://localhost:8081";

  constructor(private http: HttpClient) { }
  registerNewUser(user: UserInfo) {

    return this.http.post("http://localhost:8081/register", user,  { responseType: 'text' as 'json' });
  }

  checkBalance(token)
  {
    let tokenStr = 'Bearer ' + token;
    console.log(tokenStr);
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get(`${this.baseUrl}/checkBalance`,{headers});

  }
    public getTransactions(token){ 
      let tokenStr = 'Bearer ' + token;
      console.log(tokenStr);
      const headers = new HttpHeaders().set('Authorization', tokenStr);
      return this.http.get<Transaction[]>(`${this.baseUrl}/getTransactions`,{headers})
    }
    public getAll(token){ 
      let tokenStr = 'Bearer ' + token;
      console.log(tokenStr);
      const headers = new HttpHeaders().set('Authorization', tokenStr);
      return this.http.get<UserInfo[]>(`${this.baseUrl}/getAll`,{headers})
    }
  forgotPassword(mobile,securityQ,securityA) {
    let forgotPassRequest={
       "mobile":mobile,
       "securityQ":securityQ,
       "securityA":securityA,
     }
     return this.http.put<String>(`${this.baseUrl}/forgotPassword`,forgotPassRequest, { responseType: 'text' as 'json' });
   }

   changePassword(token, oldPassword, newPassword){
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get<String>("http://localhost:8081/changePassword/" +oldPassword+ "/" +newPassword,{headers,responseType: 'text' as 'json'});
  }
  public getuserdetails(token) 
  {
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get<UserInfo>(`${this.baseUrl}/getUserDetails`,{headers});
  }

  public editUser(user:UserInfo)
  {
    let tokenStr = 'Bearer ' + localStorage.token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
   return this.http.put(`${this.baseUrl}/editUser`,user,{headers,responseType:'text'})
  }

  public transfer(token, mobile, amount,pin){
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get<String>(`${this.baseUrl}/transfer/${mobile}/${amount}/${pin}`, {headers, responseType: 'text' as 'json' });
    return this.http.get("http://localhost:8081/transfer/" +mobile+ "/" +amount+ "/" +pin, {headers,responseType:'text'});
  }

  withdraw(token, cardnumber, amount,pin){
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get(`${this.baseUrl}/withdrawMoney/${cardnumber}/${amount}/${pin}`, {headers,responseType:'text'});
  }

  forgotPin(token,password){
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get(`${this.baseUrl}/forgotPin/${password}`, {headers,responseType:'text'});

  }

  addCard(card1:card,token) 
  {
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    console.log(card1);
    return this.http.post<String>('http://localhost:8081/addCard',card1,{headers ,responseType:'text' as 'json'});
  }

   public viewCards(token){ 
      let tokenStr = 'Bearer ' + token;
      const headers = new HttpHeaders().set('Authorization', tokenStr);
      return this.http.get<card[]>(`${this.baseUrl}/viewCards`,{headers });
    }

    deleteCard(cardNumber,token){  //sagar
      let tokenStr = 'Bearer ' + token;
      const headers = new HttpHeaders().set('Authorization', tokenStr);
      return this.http.get<String>("http://localhost:8081/deleteCard/" +cardNumber,{headers, responseType: 'text' as 'json' });
    }
    
    deleteAllCards(token){  //sagar
      let tokenStr = 'Bearer ' + token;
      const headers = new HttpHeaders().set('Authorization', tokenStr);
      console.log(token);
      return this.http.get("http://localhost:8081/deleteAllCards/",{headers, responseType: 'text' as 'json' });
    }

    addMoney(token,cardNumber,amount){  //sagar
      let tokenStr = 'Bearer ' + token;
      const headers = new HttpHeaders().set('Authorization', tokenStr);
      console.log(tokenStr);
      return this.http.get<String>("http://localhost:8081/addMoney/" +cardNumber+ "/" +amount,{headers, responseType: 'text' as 'json' });
    }
     


  disableUser(token,mobile:number){
    let tokenStr = 'Bearer ' + token;
      console.log(tokenStr);
      const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get<boolean>(`${this.baseUrl}/deleteUser/${mobile}`,{headers});
    console.log("User Disabled");
  }

  check(token){  //sagar
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    console.log(tokenStr);
    return this.http.get("http://localhost:8081/checkBalance",{headers, responseType: 'text' as 'json' });
  }

  getRole(token)
  {
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get("http://localhost:8081/getRole",{headers, responseType: 'text' as 'json' })

  }

}
