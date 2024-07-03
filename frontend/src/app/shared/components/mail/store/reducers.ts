import { createFeature, createReducer, createSelector, on } from '@ngrx/store';
import { routerNavigatedAction } from '@ngrx/router-store';

import { EmailDetailStateInterface } from '../types/emailDetailsState.interface';
import { MailStateInterface } from '../types/mailState.interface';

import { MailActions } from './actions';

const initialDetailsState: EmailDetailStateInterface = {
  status: undefined,
  emails: [],
};

const initialState: MailStateInterface = {
  inbox: {
    statuses: {},
    count: 0,
  },
  details: initialDetailsState,
  isLoading: false,
  error: null,
};

const mailFeature = createFeature({
  name: 'mail',
  reducer: createReducer(
    initialState,
    // ------------------------------------------------
    on(MailActions.getInbox, (state) => ({
      ...state,
      isLoading: true,
      error: null,
    })),
    on(MailActions.getInboxSuccess, (state, action) => ({
      ...state,
      isLoading: false,
      inbox: action.inbox,
    })),
    on(MailActions.getInboxFailed, (state, action) => ({
      ...state,
      isLoading: false,
    })),
    // ------------------------------------------------
    on(MailActions.getStatus, (state) => ({
      ...state,
      isLoading: true,
      error: null,
    })),
    on(MailActions.getStatusSuccess, (state, action) => ({
      ...state,
      details: {
        ...state.details,
        status: action.status,
      },
      isLoading: false,
    })),
    on(MailActions.getStatusFailed, (state, action) => ({
      ...state,
      details: {
        ...state.details,
        status: null,
      },
      isLoading: false,
    })),
    // ------------------------------------------------
    on(MailActions.getChain, (state) => ({
      ...state,
      isLoading: true,
      error: null,
    })),
    on(MailActions.getChainSuccess, (state, action) => ({
      ...state,
      details: {
        ...state.details,
        emails: action.emails,
      },
      isLoading: false,
    })),
    on(MailActions.getChainFailed, (state, action) => ({
      ...state,
      isLoading: false,
    })),
    // ------------------------------------------------
    on(routerNavigatedAction, (state) => ({
      ...state,
      details: initialDetailsState,
    }))
  ),
});

export const {
  name: mailFeatureKey,
  reducer: mailReducer,
  selectInbox,
  selectDetails,
  selectIsLoading,
  selectError,
} = mailFeature;

export const selectDetailsStatus = createSelector(
  selectDetails,
  (details) => details.status
);

export const selectDetailsEmails = createSelector(
  selectDetails,
  (details) => details.emails
);
