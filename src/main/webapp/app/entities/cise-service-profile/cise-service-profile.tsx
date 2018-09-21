import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './cise-service-profile.reducer';
import { ICiseServiceProfile } from 'app/shared/model/cise-service-profile.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICiseServiceProfileProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface ICiseServiceProfileState {
  search: string;
}

export class CiseServiceProfile extends React.Component<ICiseServiceProfileProps, ICiseServiceProfileState> {
  state: ICiseServiceProfileState = {
    search: ''
  };

  componentDidMount() {
    this.props.getEntities();
  }

  search = () => {
    if (this.state.search) {
      this.props.getSearchEntities(this.state.search);
    }
  };

  clear = () => {
    this.props.getEntities();
    this.setState({
      search: ''
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { ciseServiceProfileList, match } = this.props;
    return (
      <div>
        <h2 id="cise-service-profile-heading">
          <Translate contentKey="gatewayuiApp.ciseServiceProfile.home.title">Cise Service Profiles</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gatewayuiApp.ciseServiceProfile.home.createLabel">Create new Cise Service Profile</Translate>
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('gatewayuiApp.ciseServiceProfile.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseServiceProfile.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceId">Service Id</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseServiceProfile.community">Community</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseServiceProfile.country">Country</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseServiceProfile.dataFreshness">Data Freshness</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceFunction">Service Function</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseServiceProfile.seaBasin">Sea Basin</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceRole">Service Role</Translate>
                </th>
                <th>
                  <Translate contentKey="gatewayuiApp.ciseServiceProfile.serviceType">Service Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ciseServiceProfileList.map((ciseServiceProfile, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${ciseServiceProfile.id}`} color="link" size="sm">
                      {ciseServiceProfile.id}
                    </Button>
                  </td>
                  <td>{ciseServiceProfile.name}</td>
                  <td>{ciseServiceProfile.serviceId}</td>
                  <td>
                    <Translate contentKey={`gatewayuiApp.CiseCommunityType.${ciseServiceProfile.community}`} />
                  </td>
                  <td>{ciseServiceProfile.country}</td>
                  <td>
                    <Translate contentKey={`gatewayuiApp.CiseDataFreshnessType.${ciseServiceProfile.dataFreshness}`} />
                  </td>
                  <td>
                    <Translate contentKey={`gatewayuiApp.CiseFunctionType.${ciseServiceProfile.serviceFunction}`} />
                  </td>
                  <td>
                    <Translate contentKey={`gatewayuiApp.CiseSeaBasinType.${ciseServiceProfile.seaBasin}`} />
                  </td>
                  <td>
                    <Translate contentKey={`gatewayuiApp.CiseServiceRoleType.${ciseServiceProfile.serviceRole}`} />
                  </td>
                  <td>
                    <Translate contentKey={`gatewayuiApp.CiseServiceType.${ciseServiceProfile.serviceType}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${ciseServiceProfile.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ciseServiceProfile.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${ciseServiceProfile.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ ciseServiceProfile }: IRootState) => ({
  ciseServiceProfileList: ciseServiceProfile.entities
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CiseServiceProfile);
