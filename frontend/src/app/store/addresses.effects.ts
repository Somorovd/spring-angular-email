import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { AddressActions } from './addresses.actions';
import { EMPTY, catchError, map, mergeMap, of } from 'rxjs';
import { ApiService } from '../services/api.service';

@Injectable()
export class AddressesEffects {
  getAllAddresses$ = createEffect(() =>
    this.action$.pipe(
      ofType(AddressActions.getAllAddresses),
      mergeMap(() => {
        return this.apiService.getAllAddresses().pipe(
          map((addresses) =>
            AddressActions.getAllAddressesSuccess({ addresses })
          ),
          catchError((errors) => of(AddressActions.requestFailed({ errors })))
        );
      })
    )
  );

  createAddress$ = createEffect(() =>
    this.action$.pipe(
      ofType(AddressActions.createAddress),
      mergeMap((action) => {
        return this.apiService.createAddress(action.address).pipe(
          map((address) => AddressActions.createAddressSuccess({ address })),
          catchError((errors) => of(AddressActions.requestFailed({ errors })))
        );
      })
    )
  );

  constructor(private action$: Actions, private apiService: ApiService) {}
}
