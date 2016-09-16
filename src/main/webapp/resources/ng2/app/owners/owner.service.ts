import _ = require("lodash");
import {Injectable} from "@angular/core";
import {Http, Response, RequestOptionsArgs, Headers, URLSearchParams} from "@angular/http";
import {Observable} from "rxjs";
import {Owner} from "./Owner";

@Injectable()
export default class OwnerService {

  constructor(private http: Http) {
  }

  private static JsonRequestOptArgs: RequestOptionsArgs = {
    headers: new Headers({Accept: "application/json"})
  };

  getOwners(firstNameToSearch = ""): Observable<Owner[]> {
    let opts = OwnerService.JsonRequestOptArgs;

    if (firstNameToSearch) {
      let searchParams = new URLSearchParams();
      searchParams.append("firstName", firstNameToSearch);
      opts = _.assignIn({}, opts, {search: searchParams});
    }

    return this.http.get('/api/owners', opts)
      .map((res: Response)=>res.json() || []);
  }


  insertOwner(owner: Owner): Observable<any> {
    let opts = _.cloneDeep(OwnerService.JsonRequestOptArgs);
    opts.headers.append('Content-Type', 'application/json');
    return this.http.post('/api/owners', JSON.stringify(owner), opts);
  }

  deleteOwner(ownerId: number): Observable<any> {
    return this.http.delete(`/api/owners/${ownerId}`, OwnerService.JsonRequestOptArgs);
  }
}
