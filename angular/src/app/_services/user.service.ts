import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const DIRECT_API_URL = 'https://35.78.71.239:8081/digitalbooks/';
const API_URL = 'https://671e0i80l8.execute-api.ap-northeast-1.amazonaws.com/DEV/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  // getPublicContent(): Observable<any> {
  //   return this.http.get(API_URL + 'all', { responseType: 'text' });
  // }

  //author/{author-id}/books
  //digitalbook/book/save-book/{author-id}
  createBook(image : File, create : any, authorId : number){
    const formdata : FormData = new FormData();
    formdata.append('image',image);
    formdata.append('title',create.title);
    formdata.append('category',create.category);
    formdata.append('price',create.price);
    formdata.append('publisher',create.publisher);
    formdata.append('active',create.active);
    formdata.append('content',create.content);

    // return this.http.post(DIRECT_API_URL +'author/'+authorId+'/books',formdata, { responseType: 'json' });
    return this.http.post(API_URL +'digitalbook/book/save-book/'+authorId,formdata, { responseType: 'json' });
  }

  //author/{author-id}/books/{book-id}
  // digitalbook/book/save-book/{author-id}/{book-id}
  updateBook(image : File, update : any, authorId : number, bookId:number){
    const formdata : FormData = new FormData();
    formdata.append('image',image);
    formdata.append('title',update.title);
    formdata.append('category',update.category);
    formdata.append('price',update.price);
    formdata.append('publisher',update.publisher);
    formdata.append('active',update.active);
    formdata.append('content',update.content);

    // return this.http.put(DIRECT_API_URL +'author/'+authorId+'/books/'+bookId,formdata, { responseType: 'json' });
    return this.http.put(API_URL +'digitalbook/book/save-book/'+authorId+'/'+bookId,formdata, { responseType: 'json' });
  }

  // digitalbook/book/search-book/author-id/{author-id}
  getAllBooks(authorId : number): Observable<any> {
    // return this.http.get(API_URL + 'author/'+authorId, { responseType: 'json' });
    return this.http.get(API_URL + 'digitalbook/book/search-book/author-id/'+authorId, { responseType: 'json' });
  }

  // digitalbook/book/search-book/{id}
  getBook(bookId : any): Observable<any> {
    // return this.http.get(API_URL + 'test/'+bookId, { responseType: 'json' });
    return this.http.get(API_URL + 'digitalbook/book/search-book/'+bookId, { responseType: 'json' });
  }

  // author/{author-id}/books/{book-id}/{block}
  // digitalbook/book/save-book/{author-id}/{book-id}/{block}
  blockOrUnblockBook(authorId : number,bookId : number, block : string){
    // return this.http.put(API_URL + 'author/'+authorId+'/books/'+bookId+"/"+block, { responseType: 'json' });
    return this.http.put(API_URL + 'digitalbook/book/save-book/'+authorId+'/'+bookId+"/"+block, { responseType: 'json' });
  }

  //readers/{emailId}/books
  // digitalbook/user/subscribtion/subscribed-books/{email}
  getSubscribedBooks(readerEmail : string): Observable<any> {
    // return this.http.get(API_URL + 'readers/'+readerEmail+"/books", { responseType: 'json' });
    return this.http.get(API_URL + 'digitalbook/user/subscribtion/subscribed-books/'+readerEmail, { responseType: 'json' });
  }

  //readers/{email-id}/books/{subscription-id}/cancel-subscription
  // digitalbook/user/subscribtion/content/{email}/{sub-id}
  unSubscriveBook(readerEmail : string, subscriptionId : string){
    // return this.http.put(API_URL + 'readers/'+readerEmail+'/books/'+subscriptionId+"/cancel-subscription", { responseType: 'json' });
    return this.http.put(API_URL + 'digitalbook/user/subscribtion/content/'+readerEmail+'/'+subscriptionId, { responseType: 'json' });
  }

  ///{book-id}/subscribe
  // digitalbook/user/subscribtion/subscribe/{book-id}
  subscribeBook(email : string, bookId : number){
    // return this.http.post(API_URL +bookId+'/subscribe',{
    //   bookId,
    //   email
    // }, { responseType: 'json' });
    return this.http.post(API_URL +'digitalbook/user/subscribtion/subscribe/'+bookId,{
      bookId,
      email
    }, { responseType: 'json' });
  }


  //readers/{emailId}/books/{subscription-id}/read
  // digitalbook/user/subscribtion/content/{email}/{sub-id}
  getBookContent(readerEmail : string, subscriptionId : string){
    // return this.http.get(API_URL + 'readers/'+readerEmail+'/books/'+subscriptionId+"/read", { responseType: 'json' });
    return this.http.get(API_URL + 'digitalbook/user/subscribtion/content/'+readerEmail+'/'+subscriptionId, { responseType: 'json' });
  }

  //all/authors
  // getAllAuthors(): Observable<any> {
  //   return this.http.get(API_URL + 'all/authors', { responseType: 'json' });
  // }

  //'searchBook?title=&author=&publishedDate=&publisher='
  // search-book?title=&author=&publishedDate=&publisher=
  searchBook(searchQuery : any ):  Observable<any> {
    if(searchQuery === null || searchQuery === undefined)
      return this.http.get(API_URL +"digitalbook/book/"+ 'search-book?title=&author=&publishedDate=&publisher=', { responseType: 'json' });
    return this.http.get(API_URL +"digitalbook/book/"+ searchQuery, { responseType: 'json' });
  }

  // searchBook(searchQuery : any ):  Observable<any> {
  //   if(searchQuery === null || searchQuery === undefined)
  //     return this.http.get(DIRECT_API_URL + 'searchBook?title=&author=&publishedDate=&publisher=', { responseType: 'json' });
  //   return this.http.get(DIRECT_API_URL + searchQuery, { responseType: 'json' });
  // }
}
