import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICiseRuleSet, defaultValue } from 'app/shared/model/cise-rule-set.model';

export const ACTION_TYPES = {
  SEARCH_CISERULESETS: 'ciseRuleSet/SEARCH_CISERULESETS',
  FETCH_CISERULESET_LIST: 'ciseRuleSet/FETCH_CISERULESET_LIST',
  FETCH_CISERULESET: 'ciseRuleSet/FETCH_CISERULESET',
  CREATE_CISERULESET: 'ciseRuleSet/CREATE_CISERULESET',
  UPDATE_CISERULESET: 'ciseRuleSet/UPDATE_CISERULESET',
  DELETE_CISERULESET: 'ciseRuleSet/DELETE_CISERULESET',
  RESET: 'ciseRuleSet/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICiseRuleSet>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CiseRuleSetState = Readonly<typeof initialState>;

// Reducer

export default (state: CiseRuleSetState = initialState, action): CiseRuleSetState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CISERULESETS):
    case REQUEST(ACTION_TYPES.FETCH_CISERULESET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CISERULESET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CISERULESET):
    case REQUEST(ACTION_TYPES.UPDATE_CISERULESET):
    case REQUEST(ACTION_TYPES.DELETE_CISERULESET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_CISERULESETS):
    case FAILURE(ACTION_TYPES.FETCH_CISERULESET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CISERULESET):
    case FAILURE(ACTION_TYPES.CREATE_CISERULESET):
    case FAILURE(ACTION_TYPES.UPDATE_CISERULESET):
    case FAILURE(ACTION_TYPES.DELETE_CISERULESET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CISERULESETS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CISERULESET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CISERULESET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CISERULESET):
    case SUCCESS(ACTION_TYPES.UPDATE_CISERULESET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CISERULESET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/cise-rule-sets';
const apiSearchUrl = 'api/_search/cise-rule-sets';

// Actions

export const getSearchEntities: ICrudSearchAction<ICiseRuleSet> = query => ({
  type: ACTION_TYPES.SEARCH_CISERULESETS,
  payload: axios.get<ICiseRuleSet>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<ICiseRuleSet> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CISERULESET_LIST,
  payload: axios.get<ICiseRuleSet>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICiseRuleSet> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CISERULESET,
    payload: axios.get<ICiseRuleSet>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICiseRuleSet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CISERULESET,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICiseRuleSet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CISERULESET,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICiseRuleSet> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CISERULESET,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
