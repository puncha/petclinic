import {Injectable} from "@angular/core";
import {Http, Response, RequestOptionsArgs, Headers} from "@angular/http";
import {Observable} from "rxjs";

@Injectable()
export default class OwnerService {

  constructor(private http: Http) {
  }

  getOwners(firstNameToSearch = ""): Observable<any> {
    let opts: RequestOptionsArgs = {
      headers: new Headers({Accept: "application/json"})
    };

    return this.http.get('/api/owners', opts)
      .map((res: Response)=>res.json() || []);
  }
}
