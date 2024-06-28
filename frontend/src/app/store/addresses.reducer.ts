import { createReducer, on } from '@ngrx/store';

import { AddressActions } from './addresses.actions';
import { Address, Errors } from '../types/models';
import { LoadStatus } from '../types/util';

export interface AddressStateInterface {
  addresses: Address[];
  status: LoadStatus;
  errors: Errors;
}

export const initialState: AddressStateInterface = {
  addresses: [],
  status: LoadStatus.SUCCESS,
  errors: {},
};

export const addressesReducer = createReducer(
  initialState,
  // ------------------------------------------------
  on(AddressActions.getAllAddresses, (state) => ({
    ...state,
    status: LoadStatus.WAITING,
    errors: {},
  })),
  on(AddressActions.getAllAddressesSuccess, (state, action) => ({
    ...state,
    addresses: action.addresses,
    status: LoadStatus.SUCCESS,
  })),
  // ------------------------------------------------
  on(AddressActions.createAddress, (state) => ({
    ...state,
    status: LoadStatus.WAITING,
    errors: {},
  })),
  on(AddressActions.createAddressSuccess, (state, action) => ({
    ...state,
    addresses: [...state.addresses, action.address],
    status: LoadStatus.SUCCESS,
  })),
  // ------------------------------------------------
  on(AddressActions.requestFailed, (state, action) => ({
    ...state,
    status: LoadStatus.FAILED,
    errors: action.errors,
  }))
);
