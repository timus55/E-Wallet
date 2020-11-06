import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtserviceService {

  constructor(private httpClient: HttpClient) { }

  public generateTokenNumber(request) {
    return this.httpClient.post<string>("http://localhost:8081/authenticateNumber", request, {  responseType: 'text' as 'json' });
  }

  public generateTokenUsername(request) {
    return this.httpClient.post<string>("http://localhost:8081/authenticateUsername", request, {  responseType: 'text' as 'json' });
  }

  public generateTokenAadhar(request) {
    return this.httpClient.post<string>("http://localhost:8081/authenticateAadhar", request, {  responseType: 'text' as 'json' });
  }
  public welcome(token) {
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.httpClient.get<string>("http://localhost:8081/Welcome", {headers, responseType: 'text' as 'json' });
  }
  // checkBalance(token)
  // {
  //   let tokenStr = 'Bearer ' + token;
  //   console.log(tokenStr);
  //   const headers = new HttpHeaders().set('Authorization', tokenStr);
  //   console.log(headers);
  //   return this.httpClient.get("http://localhost:8080/checkBalance",{headers});
  // }
}
